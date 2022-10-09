package ru.vsu.putin_p_a.web_server.servlet_server.mapper;

import java.lang.reflect.Method;

public record MappedApplication(Class<?> mainApplication, Method subApplication) {}
