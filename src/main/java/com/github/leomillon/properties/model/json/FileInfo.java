/*
 * Copyright (c) 2015 Ekino
 */
package com.github.leomillon.properties.model.json;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class FileInfo {

    private String name;
    private String location;

    public FileInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FileInfo fileInfo = (FileInfo) o;
        return Objects.equal(name, fileInfo.name) &&
                Objects.equal(location, fileInfo.location);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, location);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("location", location)
                .toString();
    }
}
