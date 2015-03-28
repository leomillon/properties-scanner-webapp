package com.github.leomillon.properties.config;

import com.github.leomillon.properties.model.FileLocationsGroup;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "filegroups")
public class FileGroupConfig {

    private List<FileLocationsGroup> groups;

    @Nonnull
    public List<FileLocationsGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<FileLocationsGroup> groups) {
        this.groups = groups;
    }
}
