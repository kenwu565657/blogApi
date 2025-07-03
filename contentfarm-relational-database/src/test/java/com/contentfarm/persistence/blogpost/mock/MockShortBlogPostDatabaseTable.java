package com.contentfarm.persistence.blogpost.mock;

import com.contentfarm.persistence.blogpost.entity.ShortBlogPostEntity;
import lombok.Getter;

import java.util.HashMap;

@Getter
public enum MockShortBlogPostDatabaseTable {
    INSTANCE;

    /**
     * This is a mock database table for short blog posts.
     * It uses a HashMap to simulate the storage of ShortBlogPostEntity objects.
     */
    private final HashMap<String, ShortBlogPostEntity> databaseTable = new HashMap<>();
}
