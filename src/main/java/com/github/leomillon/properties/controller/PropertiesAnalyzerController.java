package com.github.leomillon.properties.controller;

import com.github.leomillon.properties.model.AnalyzedProperty;
import com.github.leomillon.properties.model.FileGroup;
import com.github.leomillon.properties.scanner.HierarchicalRegister;
import com.github.leomillon.properties.scanner.Register;
import com.github.leomillon.properties.scanner.SimpleProperty;
import com.github.leomillon.properties.service.FileGroupConfig;
import com.github.leomillon.properties.service.PropertiesLoader;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")
public class PropertiesAnalyzerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesAnalyzerController.class);

    @Resource
    private PropertiesLoader propertiesLoader;

    @Resource
    private FileGroupConfig fileGroupConfig;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        HierarchicalRegister<SimpleProperty> loadedProperties = loadProperties();
        return new ModelAndView(
                "index",
                ImmutableMap.<String, Object>builder()
                        .put("analyzedFiles", loadedProperties.getFilesOrder())
                        .put("analyzedProperties", analyzeProperties(loadedProperties))
                        .build()
        );
    }

    private Register<AnalyzedProperty> analyzeProperties(HierarchicalRegister<SimpleProperty> register) {
        return propertiesLoader.analyzeProperties(register);
    }

    private HierarchicalRegister<SimpleProperty> loadProperties() {
        try {
            FileGroup fileGroup = fileGroupConfig.getGroups().iterator().next();
            LOGGER.debug("File group to load : {}", fileGroup);
            List<String> filePaths = fileGroup.getFilePaths();
            return propertiesLoader.loadPropertiesFromFiles(filePaths);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
