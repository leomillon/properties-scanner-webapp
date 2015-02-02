package com.github.leomillon.properties.utils;

import com.google.common.io.Resources;
import org.assertj.core.data.MapEntry;
import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class PropertiesHolderTest {

    private static final String PROP_FILE_NAME = "simple.properties";

    @Test
    public void should_get_keys_values_from_properties_file() throws Exception {

        // Given
        String propFilePath = Resources.getResource(PROP_FILE_NAME).getPath();

        // When
        Map<String, String> properties = PropertiesHolder.loadProperties(propFilePath).get();

        // Then
        assertThat(properties).containsOnly(
                MapEntry.entry("sample.property.key", "true"),
                MapEntry.entry("an.other.property", "some text value"),
                MapEntry.entry("a.multiline.value", "A value on multiple lines.")
        );
    }
}