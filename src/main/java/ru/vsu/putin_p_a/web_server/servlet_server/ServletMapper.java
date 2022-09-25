package ru.vsu.putin_p_a.web_server.servlet_server;

import org.reflections.Reflections;
import ru.vsu.putin_p_a.web_server.servlet_server.servlets.Servlet;
import ru.vsu.putin_p_a.web_server.servlet_server.servlets.ServletException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ServletMapper {

    private final Map<String, Class<?>> servletMap;

    public ServletMapper() {
        this.servletMap = new HashMap<>();
    }

    public void mapServlet(String servletsPackage) {
        Reflections reflections = new Reflections(servletsPackage);
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(WebServlet.class);
        for (Class<?> c : set) {
            String annotatedPath = c.getAnnotation(WebServlet.class).value();
            servletMap.put(annotatedPath, c);
        }
    }

    public Servlet getServlet(String path) throws ServletException {
        try {
            return (Servlet) servletMap.get(path).getConstructors()[0].newInstance();
        } catch (Exception e) {
            throw new ServletException("Can't create servlet");
        }
    }
}
