package com.contentfarm.domainservice.mock.blogpost.persistence;

import com.contentfarm.outputport.blogpost.file.IBlogPostFileQueryService;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class BlogPostFileQueryServiceStub implements IBlogPostFileQueryService {

    @Override
    public byte[] getBlogPostContentByFileName(String fileName) {
        String TEST_FIlE_CLASS_PATH = "classpath:testing.md";
        try {
            File testingFile = ResourceUtils.getFile(TEST_FIlE_CLASS_PATH);
            return Files.readAllBytes(testingFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
