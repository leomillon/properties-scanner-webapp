/*
 * Copyright (c) 2015 Ekino
 */
package com.github.leomillon.properties.model.json;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import com.google.common.base.MoreObjects;

import static java.util.Objects.*;

public class FileTreeNode {

    private String name;
    @Nonnull
    private List<FileInfo> files = new ArrayList<>();
    @Nonnull
    private List<FileTreeNode> children = new ArrayList<>();

    public FileTreeNode() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nonnull
    public List<FileInfo> getFiles() {
        return files;
    }

    public void setFiles(@Nonnull List<FileInfo> files) {
        this.files = requireNonNull(files);
    }

    @Nonnull
    public List<FileTreeNode> getChildren() {
        return children;
    }

    public void setChildren(@Nonnull List<FileTreeNode> children) {
        this.children = requireNonNull(children);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("files", files)
                .add("children", children)
                .toString();
    }
}
