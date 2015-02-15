package com.github.leomillon.properties.service;

import com.github.leomillon.properties.model.AnalyzedProperty;
import com.github.leomillon.properties.scanner.EvaluatedProperty;
import com.github.leomillon.properties.scanner.HierarchicalRegister;
import com.github.leomillon.properties.scanner.HistorizedProperty;
import com.github.leomillon.properties.scanner.Register;
import com.github.leomillon.properties.scanner.SimpleProperty;
import com.github.leomillon.properties.scanner.utils.Loader;
import com.github.leomillon.properties.scanner.utils.ValueParser;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Optional;

@Service
public class PropertiesLoader {

    @Nonnull
    public HierarchicalRegister<SimpleProperty> loadPropertiesFromFiles(String... filePaths) throws IOException {

        return Loader.loadPropertiesInOrder(filePaths);
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
