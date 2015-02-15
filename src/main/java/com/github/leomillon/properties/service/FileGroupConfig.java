package com.github.leomillon.properties.service;

import com.github.leomillon.properties.model.FileGroup;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "filegroups")
public class FileGroupConfig {

    private List<FileGroup> groups;

    public List<FileGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<FileGroup> groups) {
        this.groups = groups;
    }
}
