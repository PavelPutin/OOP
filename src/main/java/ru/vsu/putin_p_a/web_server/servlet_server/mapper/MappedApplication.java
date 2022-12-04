package ru.vsu.putin_p_a.web_server.servlet_server.mapper;

import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.IController;

import java.lang.reflect.Method;
import java.util.List;
public record MappedApplication(IController controller, Method task, List<String> parameterNames) {}
