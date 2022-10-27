package ru.vsu.putin_p_a.web_server.configuration;

import java.io.IOException;
import java.util.Properties;

public interface Configuration {
    String ROOT = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    void load(String configuration) throws IOException;
    Properties getProperties();
}
