package com.github.leomillon.properties.controller;

import com.github.leomillon.properties.model.AnalyzedProperty;
import com.github.leomillon.properties.model.FilePathsGroup;
import com.github.leomillon.properties.scanner.HierarchicalRegister;
import com.github.leomillon.properties.scanner.Register;
import com.github.leomillon.properties.scanner.SimpleProperty;
import com.github.leomillon.properties.service.FileGroupService;
import com.github.leomillon.properties.service.PropertiesLoader;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
        return new ModelAndView("index", "groups", fileGroupService.getGroups());
    }

    @RequestMapping(value = "analyze/{groupName}", method = RequestMethod.GET)
    public ModelAndView analyze(@PathVariable String groupName) throws IOException {
        Optional<FilePathsGroup> optionalGroup = fileGroupService.getGroupForName(groupName);
        if (optionalGroup.isPresent()) {
            HierarchicalRegister<SimpleProperty> loadedProperties = loadProperties(optionalGroup.get());
            return new ModelAndView(
                    "analyze",
                    ImmutableMap.<String, Object>builder()
                            .put("analyzedFiles", loadedProperties.getFilesOrder())
                            .put("analyzedProperties", analyzeProperties(loadedProperties))
                            .build()
            );
        }
        throw new IllegalArgumentException("No group found for name '" + groupName + "'");
    }

    private HierarchicalRegister<SimpleProperty> loadProperties(FilePathsGroup filePathsGroup) throws IOException {
        LOGGER.debug("File group to load : {}", filePathsGroup);
        return propertiesLoader.loadPropertiesFromFiles(filePathsGroup.getFilePaths());
    }

    private Register<AnalyzedProperty> analyzeProperties(HierarchicalRegister<SimpleProperty> register) {
        return propertiesLoader.analyzeProperties(register);
    }

}
