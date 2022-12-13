package ru.vsu.putin_p_a.web_server.servlet_server.mapper;

import org.reflections.Reflections;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpRequest;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerMapper {
    private final Map<String, MappedApplication> servletMap = new HashMap<>();
    private final Map<String, MappedApplication> exceptionControllerMap = new HashMap<>();
    private MappedApplication notFound;

    {
        try {
            setNotFound(new DefaultNotFound());
        } catch (ServletMapException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isServlet(Class<?> c) {
        boolean isServlet = false;
        for (AnnotatedType annotatedInterface : c.getAnnotatedInterfaces()) {
            if (annotatedInterface.getType().equals(IController.class) ||
                    annotatedInterface.getType().equals(IExceptionController.class)) {
                isServlet = true;
                break;
            }
        }
        return isServlet;
    }

    public MappedApplication getNotFound() {
        return notFound;
    }

    public void setNotFound(IController notFound) throws ServletMapException {
        List<Method> task = Arrays.stream(notFound.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Get.class) && method.getAnnotation(Get.class).value().equals(""))
                .toList();
        if (task.size() == 1) {
            this.notFound = new MappedApplication(notFound, task.get(0), new ArrayList<>());
        } else {
            throw new ServletMapException("\"Not found\" controller can't understand what to execute.");
        }
    }

    public void mapServlet(String servletsPackage) throws ServletMapException {
        Reflections reflections = new Reflections(servletsPackage);
        Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(Controller.class)
                .stream().filter(this::isServlet)
                .collect(Collectors.toSet());

        for (Class<?> clazz : controllerClasses) {
            String annotated = clazz.getAnnotation(Controller.class).value();
            IController mapped;
            try {
                mapped = (IController) clazz.getConstructors()[0].newInstance();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new ServletMapException(String.format("Can't create servlet %s because %s.", annotated, e.getCause()));
            }

            List<Method> tasks = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Get.class))
                    .toList();

            for (Method m : tasks) {
                String application = m.getAnnotation(Get.class).value();

                List<String> paramsNames = new ArrayList<>();
                if (!(mapped instanceof IExceptionController)) {
                    Annotation[][] parameterAnnotations = m.getParameterAnnotations();
                    for (Annotation[] parameter : parameterAnnotations) {
                        for (Annotation annotation : parameter) {
                            if (annotation.annotationType().equals(Param.class)) {
                                String parameterName = ((Param) annotation).value();
                                paramsNames.add(parameterName);
                            } else {
                                throw new ServletMapException(String.format("Task %s in controller %s contains not annotated parameters.", annotated, application));
                            }
                        }
                    }
                }
                String full = annotated + application;
                if (servletMap.containsKey(full) || exceptionControllerMap.containsKey(full)) {
                    throw new ServletMapException(String.format("The controller %s already exists.", full));
                }

                if (mapped instanceof IExceptionController) {
                    exceptionControllerMap.put(full, new MappedApplication(mapped, m, paramsNames));
                } else {
                    servletMap.put(full, new MappedApplication(mapped, m, paramsNames));
                }
            }
        }
    }

    public boolean containController(HttpRequest req) {
        return servletMap.containsKey(req.getUri());
    }

    public MappedApplication getApplication(HttpRequest req) throws MissingParametersException, ServletAccessException {
        if (!servletMap.containsKey(req.getUri())) {
            throw new ServletAccessException(String.format("Nothing was found at the address: %s.", req.getUri()));
        }
        MappedApplication application = servletMap.get(req.getUri());
        boolean hasAllParameter = req.getParameters().keySet().containsAll(application.parameterNames());
        if (!hasAllParameter) {
            throw new MissingParametersException("Not all parameters were passed.");
        }
        return application;
    }

    public MappedApplication getExceptionController(String uri) throws ServletAccessException {
        if (!exceptionControllerMap.containsKey(uri)) {
            throw new ServletAccessException(String.format("Nothing was found at the address: %s.", uri));
        }
        return exceptionControllerMap.get(uri);
    }
}
