package com.github.leomillon.properties.model;

import org.testng.annotations.Test;

import java.util.Collections;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.*;

public class PropertyReaderTest {

    @Test
    public void should_get_property_key() {

        // Given
        String propertyKey = "propertyKey";
        Property property = new Property(propertyKey);

        // When
        PropertyReader propertyReader = new PropertyReader(property, new LinkedList<>());

        // Then
        assertThat(propertyReader.getKey()).isEqualTo(propertyKey);
    }

    @Test
    public void should_get_last_value_of_property() {

        // Given
        Property property = new Property("propertyKey");
        String file1Value = "file1Value";
        PropertiesFile file1 = propFile("prop1");
        property.addValue(file1Value, file1);
        String file2Value = "file2Value";
        PropertiesFile file2 = propFile("prop2");
        property.addValue(file2Value, file2);

        // When
        PropertyReader propertyReader = new PropertyReader(property, fileOrder(file1, file2));

        // Then
        assertThat(propertyReader.getLastValue()).isEqualTo(file2Value);
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