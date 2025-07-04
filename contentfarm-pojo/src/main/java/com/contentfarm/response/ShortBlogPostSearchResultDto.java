package com.contentfarm.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ShortBlogPostSearchResultDto {
    private String id;
    private String title;
    private List<String> tagList;
    private String summary;
    private String authorName;
    private String postDate;
    private String imageUrl;
}
