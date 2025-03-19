package com.contentfarm.command;

import lombok.Getter;

import java.util.List;

public record AdminDeleteBlogPostTagCommand(List<String> blogPostTagList) {

}
