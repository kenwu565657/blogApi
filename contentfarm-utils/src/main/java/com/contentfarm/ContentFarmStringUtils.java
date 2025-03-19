package com.contentfarm;

import org.apache.commons.lang3.StringUtils;

public class ContentFarmStringUtils {
    private ContentFarmStringUtils() {}
    public static boolean isBlank(String value) {
        return StringUtils.isBlank(value);
    }
}
