package com.github.leomillon.properties.config;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.leomillon.properties.model.FileLocationsGroup;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "filegroups")
public class FileGroupConfig {

    private List<FileLocationsGroup> groups;

    private List<String> groupDefinitionLocations;

    @Nullable
    public List<FileLocationsGroup> getGroups() {
        return groups;
    }

    @Nonnull
    public List<FileLocationsGroup> getGroupsOrEmpty() {
        return Optional.ofNullable(groups).orElseGet(Collections::emptyList);
    }

    public void setGroups(List<FileLocationsGroup> groups) {
        this.groups = groups;
    }

    @Nullable
    public List<String> getGroupDefinitionLocations() {
        return groupDefinitionLocations;
    }

    @Nonnull
    public List<String> getGroupDefinitionLocationsOrEmpty() {
        return Optional.ofNullable(groupDefinitionLocations).orElseGet(Collections::emptyList);
    }

    public void setGroupDefinitionLocations(List<String> groupDefinitionLocations) {
        this.groupDefinitionLocations = groupDefinitionLocations;
    }
}
