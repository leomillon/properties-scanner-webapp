package com.github.leomillon.properties.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class FileGroup {

    @Nonnull
    private String name;
    @Nonnull
    private List<File> files;

    public FileGroup(@Nonnull String name, @Nonnull List<File> files) {
        this.name = Objects.requireNonNull(name);
        this.files = ImmutableList.copyOf(files);
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public List<File> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("files", files)
                .toString();
    }
}
