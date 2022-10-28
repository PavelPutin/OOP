package ru.vsu.putin_p_a.web_server.servlet_server.mapper;

import org.reflections.Reflections;
import ru.vsu.putin_p_a.App;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpRequest;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Controller;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.WebController;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Get;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Param;
import ru.vsu.putin_p_a.web_server.servlet_server.servlets.DefaultNotFound;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerMapper {
    private final Map<String, MappedApplication> servletMap = new HashMap<>();
    private MappedApplication notFound;

    {
        try {
            setNotFound(new DefaultNotFound());
        } catch (ServletMapException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isServlet(Class<?> c) {
        boolean isServlet = false;
        for (AnnotatedType annotatedInterface : c.getAnnotatedInterfaces()) {
            if (annotatedInterface.getType().equals(Controller.class)) {
                isServlet = true;
                break;
            }
        }
        return isServlet;
    }

    public MappedApplication getNotFound() {
        return notFound;
    }

    public void setNotFound(Controller notFound) throws ServletMapException {
        List<Method> task = Arrays.stream(notFound.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Get.class) && method.getAnnotation(Get.class).value().equals(""))
                .toList();
        if (task.size() == 1) {
            this.notFound = new MappedApplication(notFound, task.get(0), new ArrayList<>());
        } else {
            throw new ServletMapException("\"Not found\" controller can't understand what to execute");
        }
    }

    public void mapServlet(String servletsPackage) throws ServletMapException {
        Reflections reflections = new Reflections(servletsPackage);
        Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(WebController.class)
                .stream().filter(ControllerMapper::isServlet)
                .collect(Collectors.toSet());

        for (Class<?> clazz : controllerClasses) {
            String annotated = clazz.getAnnotation(WebController.class).value();
            Controller mapped;
            try {
                mapped = (Controller) clazz.getConstructors()[0].newInstance();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new ServletMapException(String.format("Can't create servlet %s потому что %s", annotated, e.getCause()));
            }

            List<Method> tasks = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Get.class))
                    .toList();

            for (Method m : tasks) {
                String application = m.getAnnotation(Get.class).value();

                List<String> paramsNames = new ArrayList<>();
                Annotation[][] parameterAnnotations = m.getParameterAnnotations();
                for (Annotation[] parameter : parameterAnnotations) {
                    for (Annotation annotation : parameter) {
                        if (annotation.annotationType().equals(Param.class)) {
                            String parameterName = ((Param) annotation).value();
                            paramsNames.add(parameterName);
                        } else {
                            throw new ServletMapException(String.format("Задача %s в контролере %s содержит неаннотированные параметры", annotated, application));
                        }
                    }
                }
                String full = annotated + application;
                if (servletMap.containsKey(full)) {
                    throw new ServletMapException(String.format("Servlet %s already exists.", full));
                }

                servletMap.put(full, new MappedApplication(mapped, m, paramsNames));
            }
        }
    }

    public boolean containController(HttpRequest req) {
        return servletMap.containsKey(req.getUri());
    }

    public MappedApplication getApplication(HttpRequest req) throws MissingParametersException, ServletAccessException {
        if (!servletMap.containsKey(req.getUri())) {
            throw new ServletAccessException(String.format("По адресу %s ничего не найдено", req.getUri()));
        }
        MappedApplication application = servletMap.get(req.getUri());
        boolean hasAllParameter = req.getParameters().keySet().containsAll(application.parameterNames());
        if (!hasAllParameter) {
            throw new MissingParametersException("Переданы не все параметры");
        }
        return application;
    }
}
