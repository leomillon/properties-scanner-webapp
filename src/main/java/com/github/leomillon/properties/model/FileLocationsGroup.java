package com.github.leomillon.properties.model;

import java.util.List;
import com.google.common.base.MoreObjects;

public class FileLocationsGroup {

    private String id;

    private String name;

    private List<String> fileLocations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFileLocations() {
        return fileLocations;
    }

    public void setFileLocations(List<String> fileLocations) {
        this.fileLocations = fileLocations;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("fileLocations", fileLocations)
                .toString();
    }
}
