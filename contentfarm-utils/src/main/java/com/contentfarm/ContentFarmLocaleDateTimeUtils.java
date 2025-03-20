package com.contentfarm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ContentFarmLocaleDateTimeUtils {
    private ContentFarmLocaleDateTimeUtils() {}

    private static final DateTimeFormatter DATE_TIME_yyyy_MM_dd_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String formatTo_yyyy_MM_dd(LocalDateTime localDateTime) {
        return formatToString(localDateTime, DATE_TIME_yyyy_MM_dd_FORMATTER);
    }

    public static LocalDateTime ofNow() {
        return LocalDateTime.now();
    }

    public static LocalDateTime parseFrom_yyyy_MM_dd(String dateTimeString) {
        if (ContentFarmStringUtils.isBlank(dateTimeString)) {
            return null;
        }
        return LocalDate.parse(dateTimeString, DATE_TIME_yyyy_MM_dd_FORMATTER).atStartOfDay();
    }

    private static String formatToString(LocalDateTime localDateTime, DateTimeFormatter dateTimeFormatter) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(dateTimeFormatter);
    }
}
