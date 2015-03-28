package com.github.leomillon.properties.service;

import com.github.leomillon.properties.config.FileGroupConfig;
import com.github.leomillon.properties.model.FileLocationsGroup;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.*;

@Component
public class FileGroupService {

    @Resource
    private FileGroupConfig fileGroupConfig;

    @Nonnull
    public List<FileLocationsGroup> getGroups() {
        return fileGroupConfig.getGroups();
    }

    @Nonnull
    public Optional<FileLocationsGroup> getGroupForName(@Nonnull String name) {
        requireNonNull(name, "A group name is required.");
        List<FileLocationsGroup> groups = fileGroupConfig.getGroups();
        return groups.stream()
                .filter(group -> name.equalsIgnoreCase(group.getName()))
                .findAny();
    }

}
