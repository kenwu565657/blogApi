package com.contentfarm.command;

public class AdminDeleteBlogPostCommand {
    private final String id;

    public AdminDeleteBlogPostCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
