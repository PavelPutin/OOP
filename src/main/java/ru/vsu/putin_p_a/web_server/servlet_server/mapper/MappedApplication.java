package ru.vsu.putin_p_a.web_server.servlet_server.mapper;

import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Controller;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public record MappedApplication(Controller controller, Method task, List<String> parameterNames) {}
