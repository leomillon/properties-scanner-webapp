package com.github.leomillon.properties.service;

import com.github.leomillon.properties.model.AnalyzedProperties;
import com.google.common.io.Resources;
import org.mockito.InjectMocks;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.MockitoAnnotations.*;

public class PropertiesLoaderTest {

    private static final String FIRST_PROP_FILE_NAME = "simple.properties";
    private static final String SECOND_PROP_FILE_NAME = "second_file.properties";

    @InjectMocks
    private PropertiesLoader propertiesLoader;

    @BeforeMethod
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void should_analyze_properties_from_multiple_files() throws Exception {

        // Given
        String firstFilePath = Resources.getResource(FIRST_PROP_FILE_NAME).getPath();
        String secondFilePath = Resources.getResource(SECOND_PROP_FILE_NAME).getPath();

        // When
        AnalyzedProperties analyzedProperties =
                propertiesLoader.loadPropertiesFromFiles(firstFilePath, secondFilePath);

        // Then
        assertThat(analyzedProperties).isNotNull();
        assertThat(analyzedProperties.getPropertyReaders())
                .extracting("key", "lastValue")
                .containsOnlyOnce(
                        tuple("sample.property.key", "false"),
                        tuple("an.other.property", "some text value"),
                        tuple("a.multiline.value", "A value on multiple lines."),
                        tuple("an.new.property", "Oh! A property...")
                );
    }
}