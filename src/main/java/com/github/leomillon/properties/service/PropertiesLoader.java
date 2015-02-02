package com.github.leomillon.properties.service;

import com.github.leomillon.properties.model.AnalyzedProperties;
import com.github.leomillon.properties.model.PropertiesFile;
import com.github.leomillon.properties.utils.PropertiesHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

@Service
public class PropertiesLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoader.class);

    public AnalyzedProperties loadPropertiesFromFiles(String... filePaths) throws IOException {

        AnalyzedProperties analyzedProperties = new AnalyzedProperties();
        int index = 0;
        LinkedList<PropertiesFile> filesOrder = new LinkedList<>();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            LOGGER.debug("Loading properties file : {}", filePath);
            PropertiesFile propertiesFile = new PropertiesFile(String.valueOf(index++), file.getName());
            filesOrder.add(propertiesFile);
            for (Map.Entry<String, String> propertyEntry : PropertiesHolder.loadProperties(filePath).get().entrySet()) {
                analyzedProperties.addProperty(propertiesFile, propertyEntry.getKey(), propertyEntry.getValue());
            }
        }

        analyzedProperties.setFilesOrder(filesOrder);

        return analyzedProperties;
    }

}
