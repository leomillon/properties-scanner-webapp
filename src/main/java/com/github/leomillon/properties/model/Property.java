package com.github.leomillon.properties.model;

import javax.annotation.Nonnull;
import java.util.Map;

public class Property {

    @Nonnull
    private final String key;
    @Nonnull
    private final PropertyHistory history = new PropertyHistory();

    public Property(@Nonnull String key) {
        this.key = key;
    }

    @Nonnull
    public String getKey() {
        return key;
    }

    public void addValue(String value, PropertiesFile file) {
        history.addValue(value, file);
    }

    @Nonnull
    public Map<PropertiesFile, String> getHistory() {
        return history.getValues();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property property = (Property) o;

        if (!key.equals(property.key)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
