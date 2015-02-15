package com.github.leomillon.properties.config;

import com.github.leomillon.properties.model.FilePathsGroup;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "filegroups")
public class FileGroupConfig {

    private List<FilePathsGroup> groups;

    @Nonnull
    public List<FilePathsGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<FilePathsGroup> groups) {
        this.groups = groups;
    }
}
