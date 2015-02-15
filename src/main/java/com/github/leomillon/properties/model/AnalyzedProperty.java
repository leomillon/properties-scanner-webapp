package com.github.leomillon.properties.model;

import com.github.leomillon.properties.scanner.EvaluatedProperty;
import com.github.leomillon.properties.scanner.HistorizedProperty;
import com.github.leomillon.properties.scanner.PropFile;
import com.github.leomillon.properties.scanner.Property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.TreeMap;

import static java.util.Objects.*;

public class AnalyzedProperty implements Property {

    @Nonnull
    private HistorizedProperty historizedProperty;
    @Nonnull
    private EvaluatedProperty evaluatedProperty;

    public AnalyzedProperty(@Nonnull HistorizedProperty historizedProperty,
                            @Nonnull EvaluatedProperty evaluatedProperty) {
        this.historizedProperty = requireNonNull(historizedProperty);
        this.evaluatedProperty = requireNonNull(evaluatedProperty);
        assert Objects.equals(historizedProperty.getKey(), evaluatedProperty.getKey());
    }

    @Nonnull
    @Override
    public String getKey() {
        return evaluatedProperty.getKey();
    }

    @Nullable
    @Override
    public String getValue() {
        return evaluatedProperty.getRawValue();
    }

    @Nullable
    public String getInterpretedValue() {
        return evaluatedProperty.getInterpretedValue();
    }

    @Nonnull
    public TreeMap<PropFile, String> getHistory() {
        return historizedProperty.getHistory();
    }

    public HistorizedProperty.State getState() {
        return historizedProperty.getState();
    }

    @Nullable
    public String getLastValue() {
        return historizedProperty.getLastValue();
    }
}
