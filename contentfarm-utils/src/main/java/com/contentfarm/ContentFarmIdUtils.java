package com.contentfarm;

import java.util.UUID;

public class ContentFarmIdUtils {
    private ContentFarmIdUtils() {}

    public static String ofRandom() {
        return UUID.randomUUID().toString();
    }

}
