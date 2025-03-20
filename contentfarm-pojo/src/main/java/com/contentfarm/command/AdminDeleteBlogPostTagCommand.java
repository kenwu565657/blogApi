package com.contentfarm.command;

import java.util.List;

public record AdminDeleteBlogPostTagCommand(List<String> blogPostTagList) {

}
