package com.github.leomillon.properties.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.*;

public class PropertyReader {

    @Nonnull
    private Property property;
    @Nonnull
    private LinkedList<PropertiesFile> filesOrder = new LinkedList<>();

    public PropertyReader(@Nonnull Property property, @Nonnull LinkedList<PropertiesFile> filesOrder) {
        this.property = requireNonNull(property);
        this.filesOrder = requireNonNull(filesOrder);
    }

    @Nonnull
    public String getKey() {
        return property.getKey();
    }

    @Nullable
    public String getLastValue() {

        Map<PropertiesFile, String> history = property.getHistory();

        Iterator<PropertiesFile> propertiesFileIterator = filesOrder.descendingIterator();
        while (propertiesFileIterator.hasNext()) {
            PropertiesFile propertiesFile = propertiesFileIterator.next();
            if (history.containsKey(propertiesFile)) {
                return history.get(propertiesFile);
            }
        }
        return null;
    }
}
