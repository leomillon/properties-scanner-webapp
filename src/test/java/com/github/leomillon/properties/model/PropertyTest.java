package com.github.leomillon.properties.model;

import org.assertj.core.data.MapEntry;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

public class PropertyTest {

    @Test
    public void should_add_values() {

        // Given
        PropertiesFile file1 = propFile("prop1");
        PropertiesFile file2 = propFile("prop2");
        PropertiesFile file3 = propFile("prop3");
        Property property = new Property("propertyKey");
        String file1Value = "file1Value";
        String file3Value = "file3Value";

        // When
        property.addValue(file1Value, file1);
        property.addValue(null, file2);
        property.addValue(file3Value, file3);

        // Then
        assertThat(property.getHistory())
                .containsEntry(file1, file1Value)
                .containsEntry(file2, null)
                .containsEntry(file3, file3Value);
    }

    @Test
    public void should_override_value_of_same_file_for_same_key() {

        // Given
        PropertiesFile file = propFile("prop1");
        Property property = new Property("propertyKey");
        String firstValue = "firstValue";
        String secondValue = "secondValue";

        // When
        property.addValue(firstValue, file);
        property.addValue(secondValue, file);

        // Then
        assertThat(property.getHistory()).containsOnly(MapEntry.entry(file, secondValue));
    }

    private static PropertiesFile propFile(String id) {
        return new PropertiesFile(id, "file-" + id);
    }
}