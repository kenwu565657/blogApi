package com.contentfarm.web.controller.blogpost;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
public class BlogPostSummaryDto {
    private String id;
    private String title;
    private String summary;
    private List<String> tagList;
    private String authorId;
    private String authorName;
    private LocalDateTime createdDateTime;
}
