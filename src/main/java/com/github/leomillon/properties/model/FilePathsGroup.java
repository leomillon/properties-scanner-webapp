package com.github.leomillon.properties.model;

import com.google.common.base.MoreObjects;

import java.util.List;

public class FilePathsGroup {

    private String name;

    private List<String> filePaths;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("filePaths", filePaths)
                .toString();
    }
}
