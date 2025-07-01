package com.contentfarm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ContentFarmIdUtilsTest {
    @Test
    void ofRandom() {
        String newId = ContentFarmIdUtils.ofRandom();
        Assertions.assertNotNull(newId);
        Assertions.assertFalse(newId.isEmpty());
    }
}