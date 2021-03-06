package com.github.leomillon.properties.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Resource;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.leomillon.properties.model.AnalyzedProperty;
import com.github.leomillon.properties.model.FileLocationsGroup;
import com.github.leomillon.properties.model.json.FileLocations;
import com.github.leomillon.properties.scanner.HierarchicalRegister;
import com.github.leomillon.properties.scanner.Register;
import com.github.leomillon.properties.scanner.SimpleProperty;
import com.github.leomillon.properties.service.FileGroupService;
import com.github.leomillon.properties.service.PropertiesLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static java.util.stream.Collectors.*;

@Controller
@RequestMapping("/")
public class PropertiesAnalyzerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesAnalyzerController.class);

    @Resource
    private PropertiesLoader propertiesLoader;
    @Resource
    private FileGroupService fileGroupService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index", "groups", sortByName(fileGroupService.getGroups()));
    }

    @Nonnull
    private static List<FileLocationsGroup> sortByName(Collection<FileLocationsGroup> groups) {
        return groups.stream().sorted(groupByNameComparator()).collect(toList());
    }

    @Nonnull
    private static Comparator<FileLocationsGroup> groupByNameComparator() {
        return Ordering.natural().onResultOf(FileLocationsGroup::getName);
    }

    @RequestMapping(value = "analyze/{groupId}", method = RequestMethod.GET)
    public ModelAndView analyze(@PathVariable String groupId) throws IOException {
        Optional<FileLocationsGroup> optionalGroup = fileGroupService.getGroupById(groupId);
        if (optionalGroup.isPresent()) {
            return viewForGroup(optionalGroup.get());
        }
        throw new IllegalArgumentException("No group found for id '" + groupId + "'");
    }

    @RequestMapping(value = "analyze", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView analyze(@RequestBody FileLocations fileLocations) throws IOException {
        return viewForGroup(fileGroupService.groupFromLocations(fileLocations.getLocations()));
    }

    private ModelAndView viewForGroup(FileLocationsGroup group) throws IOException {
        HierarchicalRegister<SimpleProperty> loadedProperties = loadProperties(group);
        return new ModelAndView(
                "analyze",
                ImmutableMap.<String, Object>builder()
                        .put("analyzedFiles", loadedProperties.getFilesOrder())
                        .put("analyzedProperties", analyzeProperties(loadedProperties))
                        .build()
        );
    }

    private HierarchicalRegister<SimpleProperty> loadProperties(FileLocationsGroup fileLocationsGroup) throws IOException {
        LOGGER.debug("File group to load : {}", fileLocationsGroup);
        return propertiesLoader.loadPropertiesFromFiles(fileLocationsGroup.getFileLocations());
    }

    private Register<AnalyzedProperty> analyzeProperties(HierarchicalRegister<SimpleProperty> register) {
        return propertiesLoader.analyzeProperties(register);
    }

}
