package com.contentfarm.command;

import java.util.List;

public record AdminAddBlogPostTagCommand(List<String> blogPostTagList){}
