package com.github.leomillon.properties.service;

import com.github.leomillon.properties.config.FileGroupConfig;
import com.github.leomillon.properties.model.FilePathsGroup;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.*;

@Component
public class FileGroupService {

    @Resource
    private FileGroupConfig fileGroupConfig;

    @Nonnull
    public List<FilePathsGroup> getGroups() {
        return fileGroupConfig.getGroups();
    }

    @Nonnull
    public Optional<FilePathsGroup> getGroupForName(@Nonnull String name) {
        requireNonNull(name, "A group name is required.");
        List<FilePathsGroup> groups = fileGroupConfig.getGroups();
        return groups.stream()
                .filter(group -> name.equalsIgnoreCase(group.getName()))
                .findAny();
    }

}
