package net.erasmatov.crudapi.annotation;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ExcludeAnnotationExclusionStrategy implements ExclusionStrategy {
    private static final ExcludeAnnotationExclusionStrategy strategy = new ExcludeAnnotationExclusionStrategy();

    private ExcludeAnnotationExclusionStrategy() {
    }

    public static ExcludeAnnotationExclusionStrategy getStrategy() {
        return strategy;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getAnnotation(Exclude.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }

}
