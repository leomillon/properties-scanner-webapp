package com.github.leomillon.properties.model;

import org.testng.annotations.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class AnalyzedPropertiesTest {

    @Test
    public void should_get_property_reader_for_each_property() {

        // Given
        AnalyzedProperties analyzedProperties = new AnalyzedProperties();
        PropertiesFile file1 = propFile("file1");
        String propertyKey1 = "property1";
        String propertyValue1 = "value";
        analyzedProperties.addProperty(file1, propertyKey1, propertyValue1);
        PropertiesFile file2 = propFile("file2");
        String propertyKey2 = "property2";
        String propertyValue2 = "value2";
        analyzedProperties.addProperty(file2, propertyKey2, propertyValue2);
        analyzedProperties.setFilesOrder(fileOrder(file1, file2));

        // When
        List<PropertyReader> propertyReaders = analyzedProperties.getPropertyReaders();

        // Then
        assertThat(propertyReaders)
                .extracting("key")
                .containsOnlyOnce(propertyKey1, propertyKey2);
    }

    private static LinkedList<PropertiesFile> fileOrder(PropertiesFile... propertiesFiles) {
        LinkedList<PropertiesFile> fileOrder = new LinkedList<>();
        Collections.addAll(fileOrder, propertiesFiles);
        return fileOrder;
    }

    private static PropertiesFile propFile(String id) {
        return new PropertiesFile(id, "file-" + id);
    }
}