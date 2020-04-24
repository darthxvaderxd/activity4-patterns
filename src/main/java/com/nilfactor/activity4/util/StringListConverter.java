package com.nilfactor.activity4.util;

import java.util.Arrays;
import java.util.List;

import javax.interceptor.Interceptors;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@Interceptors(LogInterceptor.class)
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    @Interceptors(LogInterceptor.class)
    public String convertToDatabaseColumn(List<String> stringList) {
        return String.join(SPLIT_CHAR, stringList);
    }

    @Override
    @Interceptors(LogInterceptor.class)
    public List<String> convertToEntityAttribute(String string) {
        return Arrays.asList(string.split(SPLIT_CHAR));
    }
}