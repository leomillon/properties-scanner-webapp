package com.github.leomillon.properties.config;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "filegroups")
public class FileGroupConfig {

    private List<String> groupDefinitionLocations;

    @Nullable
    public List<String> getGroupDefinitionLocations() {
        return groupDefinitionLocations;
    }

    public void setGroupDefinitionLocations(List<String> groupDefinitionLocations) {
        this.groupDefinitionLocations = groupDefinitionLocations;
    }

    @Nonnull
    public List<String> getGroupDefinitionLocationsOrEmpty() {
        return Optional.ofNullable(groupDefinitionLocations).orElseGet(Collections::emptyList);
    }
}
