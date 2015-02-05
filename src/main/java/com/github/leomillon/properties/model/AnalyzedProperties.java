package com.github.leomillon.properties.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.*;

public class AnalyzedProperties {

    private Collection<Property> properties = new ArrayList<>();
    private LinkedList<PropertiesFile> filesOrder = new LinkedList<>();

    public LinkedList<PropertiesFile> getFilesOrder() {
        return filesOrder;
    }

    public void setFilesOrder(@Nonnull LinkedList<PropertiesFile> filesOrder) {
        this.filesOrder = new LinkedList<>(requireNonNull(filesOrder));
    }

    public void addProperty(@Nonnull PropertiesFile propertiesFile, @Nonnull String key, @Nullable String value) {
        getOrCreatePropertyForKey(key).addValue(value, requireNonNull(propertiesFile));
    }

    public List<PropertyReader> getPropertyReaders() {
        return properties.stream()
                .map(property -> new PropertyReader(property, filesOrder))
                .sorted((p1, p2) -> p1.getKey().compareTo(p2.getKey()))
                .collect(Collectors.toList());
    }

    private Property getOrCreatePropertyForKey(@Nonnull String key) {
        Optional<Property> propertyOptional = properties.stream()
                .filter(p -> key.equals(p.getKey()))
                .findAny();
        if (propertyOptional.isPresent()) {
            return propertyOptional.get();
        }
        Property newProperty = new Property(key);
        properties.add(newProperty);
        return newProperty;
    }
}
