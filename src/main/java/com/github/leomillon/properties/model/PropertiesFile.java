package com.github.leomillon.properties.model;

import javax.annotation.Nonnull;

import static java.util.Objects.*;

public class PropertiesFile {

    @Nonnull
    private final String id;
    @Nonnull
    private final String name;

    public PropertiesFile(@Nonnull String id, @Nonnull String name) {
        this.id = requireNonNull(id);
        this.name = requireNonNull(name);
    }

    @Nonnull
    public String getId() {
        return id;
    }

    @Nonnull
    public String getName() {
        return name;
    }
}
