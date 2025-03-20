package com.contentfarm.outputport.blogpost.file;

public interface IBlogPostFileQueryService {
    byte[] getBlogPostContentByFileName(String fileName);
}
