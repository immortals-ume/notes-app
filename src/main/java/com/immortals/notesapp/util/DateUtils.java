package com.immortals.notesapp.util;

import java.time.Instant;
import java.time.LocalDateTime;

public final class DateUtils {

    private DateUtils(){
        throw new IllegalStateException("DateUtils Class");
    }
    public static LocalDateTime getLocalDateTime(){
        return LocalDateTime.now();
    }

    public static Instant getInstant(){
        return Instant.now();
    }
}
