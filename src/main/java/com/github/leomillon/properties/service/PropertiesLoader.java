package com.github.leomillon.properties.service;

import com.github.leomillon.properties.model.AnalyzedProperty;
import com.github.leomillon.properties.scanner.EvaluatedProperty;
import com.github.leomillon.properties.scanner.HierarchicalRegister;
import com.github.leomillon.properties.scanner.HistorizedProperty;
import com.github.leomillon.properties.scanner.Register;
import com.github.leomillon.properties.scanner.SimpleProperty;
import com.github.leomillon.properties.scanner.descriptor.PropFileDescriptor;
import com.github.leomillon.properties.scanner.descriptor.PropFilePathDescriptor;
import com.github.leomillon.properties.scanner.utils.Loader;
import com.github.leomillon.properties.scanner.utils.ValueParser;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PropertiesLoader {

    @Resource
    private FileRetrieverService fileRetrieverService;

    @Nonnull
    public HierarchicalRegister<SimpleProperty> loadPropertiesFromFiles(@Nonnull Iterable<String> fileLocations) throws IOException {

        return Loader.loadPropertiesInOrder(fileLocationsToDescriptors(fileLocations));
    }

    @Nonnull
    private Iterable<PropFileDescriptor> fileLocationsToDescriptors(@Nonnull Iterable<String> fileLocations) {
        return StreamSupport.stream(fileLocations.spliterator(), false)
                .map(locationToDescriptor())
                .collect(Collectors.toList());
    }

    @Nonnull
    private Function<String, PropFileDescriptor> locationToDescriptor() {
        return fileLocation -> fileRetrieverService.isValidUrl(fileLocation)
                ? new PropFileUrlDescriptor(fileLocation, urlToInputStream())
                : new PropFilePathDescriptor(fileLocation);
    }

    @Nonnull
    private Function<String, InputStream> urlToInputStream() {
        return url -> {
            String contentFromUrl = null;
            try {
                contentFromUrl = fileRetrieverService.getContentFromUrl(url);
            } catch (URISyntaxException | IOException e) {
                throw new IllegalStateException("Unable to retrieve file from url", e);
            }
            return new ByteArrayInputStream(contentFromUrl.getBytes());
        };
    }

    @Nonnull
    public Register<AnalyzedProperty> analyzeProperties(HierarchicalRegister<SimpleProperty> register) {
        Register<HistorizedProperty> historizedProperties = register.getHistorizedProperties();
        Register<EvaluatedProperty> evaluatedProperties = ValueParser.evaluateProperties(register.getFinalRegister());
        assert historizedProperties.getKeys().containsAll(evaluatedProperties.getKeys());
        Register.Builder<AnalyzedProperty> analyzedPropertiesBuilder = Register.builder();
        for (HistorizedProperty historizedProperty : historizedProperties) {
            String key = historizedProperty.getKey();
            Optional<EvaluatedProperty> optionalEvaluatedProp = evaluatedProperties.getPropertyForKey(key);
            if (optionalEvaluatedProp.isPresent()) {
                analyzedPropertiesBuilder.addProperty(
                        new AnalyzedProperty(historizedProperty, optionalEvaluatedProp.get())
                );
            }
            else {
                throw new IllegalArgumentException("Unable to find evaludated property for key " + key);
            }
        }
        return analyzedPropertiesBuilder.build();
    }

}
