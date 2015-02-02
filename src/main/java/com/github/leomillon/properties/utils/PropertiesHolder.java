package com.github.leomillon.properties.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesHolder.class);

    private Map<String, String> properties = new HashMap<>();

    public static PropertiesHolder loadProperties(String filePath) throws IOException {
        PropertiesHolder propertiesHolder = new PropertiesHolder();
        try (FileInputStream fileIS = new FileInputStream(filePath)) {
            Properties props = new Properties();
            props.load(fileIS);

            for (Map.Entry<Object, Object> propEntry : props.entrySet()) {
                LOGGER.debug("Add property: {} = {}", propEntry.getKey(), propEntry.getValue());
                propertiesHolder.properties.put(String.valueOf(propEntry.getKey()), String.valueOf(propEntry.getValue()));
            }
        }
        return propertiesHolder;
    }

    public Map<String, String> get() {
        return new HashMap<>(properties);
    }

}
