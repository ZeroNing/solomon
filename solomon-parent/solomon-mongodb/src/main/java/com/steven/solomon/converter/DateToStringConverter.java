package com.steven.solomon.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.time.LocalDateTime;
@ReadingConverter
@WritingConverter
public class DateToStringConverter implements Converter<LocalDateTime, String> {
    @Override
    public String convert(LocalDateTime localDateTime) {
        return localDateTime.toString() + 'Z';
    }
}
