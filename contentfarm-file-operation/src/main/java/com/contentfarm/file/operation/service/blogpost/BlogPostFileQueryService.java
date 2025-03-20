package com.contentfarm.file.operation.service.blogpost;

import com.contentfarm.file.operation.springboot.starter.exception.FileOperationException;
import com.contentfarm.file.operation.springboot.starter.service.FileStorageService;
import com.contentfarm.outputport.blogpost.file.IBlogPostFileQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class BlogPostFileQueryService implements IBlogPostFileQueryService {
    private final FileStorageService fileStorageService;

    @Override
    public byte[] getBlogPostContentByFileName(String contentFileName) {
        if (!isEndWithExpectedFileExtension(contentFileName)) {
            throw FileOperationException.ofFileNameNotExist();
        }
        return fileStorageService.downloadFile("contentfarmblogpost", MessageFormat.format("{0}/{1}", "blog-post-content", contentFileName));
    }

    private boolean isEndWithExpectedFileExtension(String fileName) {
        Pattern pattern = Pattern.compile(".*\\.(?:md|txt)$");
        Matcher matcher = pattern.matcher(fileName);
        return matcher.matches();
    }
}
