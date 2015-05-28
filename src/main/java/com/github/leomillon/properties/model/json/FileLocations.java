/*
 * Copyright (c) 2015 Ekino
 */
package com.github.leomillon.properties.model.json;

import java.util.List;
import com.google.common.base.MoreObjects;

public class FileLocations {

    private List<String> locations;

    public FileLocations() {
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("locations", locations)
                .toString();
    }
}
