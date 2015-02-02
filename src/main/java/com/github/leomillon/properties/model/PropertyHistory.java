package com.github.leomillon.properties.model;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class PropertyHistory {

    @Nonnull
    private Map<PropertiesFile, String> values = new HashMap<>();

    public void addValue(String value, PropertiesFile file) {
        values.put(file, value);
    }

    @Nonnull
    public Map<PropertiesFile, String> getValues() {
        return values;
    }

}
