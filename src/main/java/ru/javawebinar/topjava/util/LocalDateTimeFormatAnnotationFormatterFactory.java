package ru.javawebinar.topjava.util;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class LocalDateTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalDateTimeFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> fieldTypes = new HashSet<Class<?>>(1, 1);
        fieldTypes.add(LocalDateTime.class);
        return fieldTypes;
    }

    @Override
    public Parser<?> getParser(LocalDateTimeFormat annotation, Class<?> fieldType) {
        return new LocalDTFormatter();
    }

    @Override
    public Printer<?> getPrinter(LocalDateTimeFormat annotation, Class<?> fieldType) {
        return new LocalDTFormatter();
    }

    public static class LocalDTFormatter implements Formatter<LocalDateTime> {

        @Override
        public String print(LocalDateTime localDateTime, Locale locale) {
            return DateTimeUtil.toString(localDateTime);
        }

        @Override
        public LocalDateTime parse(String text, Locale locale) throws ParseException {
            return DateTimeUtil.parseLocalDateTime(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }
}