package com.immortals.notesapp.util;

import java.time.LocalDateTime;

public class DateUtils {

    private DateUtils(){

    }
    public static LocalDateTime getLocalDateTime(){
        return LocalDateTime.now();
    }
}
