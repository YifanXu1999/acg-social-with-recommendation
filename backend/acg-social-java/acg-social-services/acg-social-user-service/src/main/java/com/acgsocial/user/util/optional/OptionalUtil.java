package com.acgsocial.user.util.optional;

import java.util.Optional;

public class OptionalUtil {
    public static <T> T getOrElse(T value, T defaultValue) {
        return Optional.ofNullable(value).orElse(defaultValue);
    }
}
