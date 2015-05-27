package com.github.leomillon.properties.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Resource;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.codec.digest.DigestUtils;

import com.github.leomillon.properties.config.FileGroupConfig;
import com.github.leomillon.properties.model.FileLocationsGroup;
import com.github.leomillon.properties.model.json.FileInfo;
import com.github.leomillon.properties.model.json.FileTreeNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import static java.util.Objects.*;
import static java.util.stream.Collectors.*;

@Component
public class FileGroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileGroupService.class);

    @Resource
    private FileGroupConfig fileGroupConfig;

    @Nonnull
    public Collection<FileLocationsGroup> getGroups() {
        return groupsFromJson();
    }

    @Nonnull
    public Optional<FileLocationsGroup> getGroupById(@Nonnull String id) {
        requireNonNull(id, "A group id is required.");
        return getGroups().stream()
                .filter(group -> id.equalsIgnoreCase(group.getId()))
                .findAny();
    }

    @Nonnull
    private Collection<FileLocationsGroup> groupsFromJson() {
        List<String> groupDefinitionLocations = fileGroupConfig.getGroupDefinitionLocationsOrEmpty();
        LOGGER.debug("groupDefinitionLocations : {}", groupDefinitionLocations);
        Collection<FileTreeNode> trees = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        groupDefinitionLocations.forEach(groupDefinitionLocation -> {
            try {
                FileTreeNode tree = mapper.readValue(new File(groupDefinitionLocation), FileTreeNode.class);
                LOGGER.debug("Tree from JSON file {} : {}", groupDefinitionLocation, tree);
                trees.add(tree);
            } catch (IOException e) {
                LOGGER.error("Unable to read JSON at location :  {}", groupDefinitionLocation, e);
            }
        });

        List<FileLocationsGroup> groups = new ArrayList<>();
        trees.forEach(tree -> groups.addAll(treeToFileLocationGroup(tree)));
        return groups;
    }

    @Nonnull
    private static Collection<FileLocationsGroup> treeToFileLocationGroup(@Nonnull FileTreeNode tree) {
        return treeToFileLocationGroup(tree, Collections.emptyList());
    }

    @Nonnull
    private static Collection<FileLocationsGroup> treeToFileLocationGroup(@Nonnull FileTreeNode tree,
                                                                          @Nonnull Collection<String> parentFileLocations) {
        List<String> currentFileLocations = new ArrayList<>(parentFileLocations);
        currentFileLocations.addAll(treeToFileLocations(tree));

        if (tree.getChildren().isEmpty()) {
            if (currentFileLocations.isEmpty()) {
                return Collections.emptyList();
            }

            if (Strings.isNullOrEmpty(tree.getName())) {
                LOGGER.error("The tree node leaf as an undefined name {}", tree);
                return Collections.emptyList();
            }
            FileLocationsGroup group = new FileLocationsGroup();
            group.setName(tree.getName());
            group.setFileLocations(currentFileLocations);
            group.setId(generateId(currentFileLocations));
            return Collections.singletonList(group);
        }

        List<FileLocationsGroup> groups = new ArrayList<>();
        tree.getChildren().forEach(child -> groups.addAll(treeToFileLocationGroup(child, currentFileLocations)));
        return groups;
    }

    @Nonnull
    private static String generateId(@Nonnull Iterable<String> locations) {
        StringBuilder stringBuilder = new StringBuilder();
        locations.forEach(stringBuilder::append);
        return DigestUtils.md5Hex(stringBuilder.toString());
    }

    @Nonnull
    private static Collection<String> treeToFileLocations(@Nonnull FileTreeNode tree) {
        requireNonNull(tree);
        return tree.getFiles().stream()
                .map(FileInfo::getLocation)
                .collect(toList());
    }

}
