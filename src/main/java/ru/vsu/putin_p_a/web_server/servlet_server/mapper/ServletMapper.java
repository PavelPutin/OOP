package ru.vsu.putin_p_a.web_server.servlet_server.mapper;

import org.reflections.Reflections;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpRequest;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpResponse;
import ru.vsu.putin_p_a.web_server.servlet_server.servlets.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ServletMapper {

    private final Map<String, MappedApplication> servletMap = new HashMap<>();
    private Class<Servlet> notFound;

    private static boolean isServlet(Class<?> c) {
        boolean isServlet = false;
        for (AnnotatedType annotatedInterface : c.getAnnotatedInterfaces()) {
            if (annotatedInterface.getType() == Servlet.class) {
                isServlet = true;
                break;
            }
        }
        return isServlet;
    }

    public void setNotFound(Class<Servlet> notFound) throws ServletMapException {
        if (Arrays.stream(notFound.getDeclaredMethods())
                .anyMatch(method -> method.isAnnotationPresent(Get.class)
                        && method.getAnnotation(Get.class).value().equals(""))) {
            this.notFound = notFound;
        } else {
            throw new ServletMapException("\"Not found\" application doesn't have executable part");
        }
    }

    public void mapServlet(String servletsPackage) throws ServletMapException {
        Reflections reflections = new Reflections(servletsPackage);
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(WebServlet.class);
        for (Class<?> c : set) {
            if (isServlet(c)) {
                String annotatedPath = c.getAnnotation(WebServlet.class).value();
                List<Method> subApplication = Arrays.stream(c.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(Get.class))
                        .toList();

                for (Method m : subApplication) {
                    String subPath = m.getAnnotation(Get.class).value();
                    List<String> paramsNames = new ArrayList<>();
                    Annotation[][] annotations = m.getParameterAnnotations();
                    for (Annotation[] param : annotations) {
                        for (Annotation annotation : param) {
                            if (annotation.annotationType().equals(Param.class)) {
                                paramsNames.add(((Param) annotation).value());
                            }
                        }
                    }
                    String fullPath = annotatedPath + "/" + subPath;
                    if (servletMap.containsKey(fullPath)) {
                        throw new ServletMapException(String.format("Servlet %s already exists.", fullPath));
                    }

                    servletMap.put(fullPath, new MappedApplication(c, m));
                }
            }
        }
    }

    public Servlet getServletOrNotFound(String path) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        try {
            return (Servlet) servletMap.get(path).mainApplication().getConstructor().newInstance();
        } catch (Exception e) {
            return notFound.getConstructor().newInstance();
        }
    }

    public Method getSubApplication(String path) throws NoSuchMethodException {
        try {
            return servletMap.get(path).subApplication();
        } catch (Exception e) {
            return notFound.getMethod("doGet");
        }

    }
}
