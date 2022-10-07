package ru.vsu.putin_p_a.web_server.servlet_server.mapper;

import org.reflections.Reflections;
import ru.vsu.putin_p_a.web_server.servlet_server.servlets.DefaultNotFound;
import ru.vsu.putin_p_a.web_server.servlet_server.servlets.Servlet;
import ru.vsu.putin_p_a.web_server.servlet_server.servlets.WebServlet;

import java.lang.reflect.AnnotatedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ServletMapper {

    private final Map<String, Class<?>> servletMap = new HashMap<>();
    private Servlet notFound = new DefaultNotFound();

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

    public void setNotFound(Servlet notFound) {
        this.notFound = notFound;
    }

    public void mapServlet(String servletsPackage) {
        Reflections reflections = new Reflections(servletsPackage);
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(WebServlet.class);
        for (Class<?> c : set) {
            if (isServlet(c)) {
                String annotatedPath = c.getAnnotation(WebServlet.class).value();
                servletMap.put(annotatedPath, c);
            }
        }
    }

    public Servlet getServletOrNotFound(String path) {
        try {
            return (Servlet) servletMap.get(path).getConstructors()[0].newInstance();
        } catch (Exception e) {
            return notFound;
        }
    }
}
