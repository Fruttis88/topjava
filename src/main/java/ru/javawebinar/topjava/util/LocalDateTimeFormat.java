package ru.javawebinar.topjava.util;

import java.lang.annotation.*;

@Target(value={ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LocalDateTimeFormat {
}