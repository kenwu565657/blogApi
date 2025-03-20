package com.contentfarm.persistence.blogpost.service;

import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;
import com.contentfarm.persistence.blogpost.dao.IBlogPostDao;
import com.contentfarm.persistence.blogpost.dao.IBlogPostTagDao;
import com.contentfarm.persistence.blogpost.entity.BlogPostTagEntity;
import com.contentfarm.persistence.blogpost.mapper.BlogPostEntityMapper;
import com.contentfarm.file.operation.springboot.starter.service.FileStorageService;
import com.contentfarm.outputport.blogpost.persistence.IBlogPostPersistenceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogPostPersistenceQueryService implements IBlogPostPersistenceQueryService {
    private final IBlogPostDao blogPostDao;
    private final IBlogPostTagDao blogPostTagDao;
    private final BlogPostEntityMapper blogPostEntityMapper;
    private final FileStorageService fileStorageService;

    @Override
    public BlogPostDomainModel getById(String id) {
        var blogPostEntity = blogPostDao.getReferenceById(id);
        return blogPostEntityMapper.mapToBlogPostDomainModel(blogPostEntity);
    }

    @Override
    public List<String> findTagList() {
        return blogPostTagDao.findAll().stream().map(BlogPostTagEntity::getTagName).toList();
    }

    @Override
    public byte[] getBlogPostContentById(String id) {
        var contentFileNameProjection = blogPostDao.getContentFileNameById(id);
        var contentFileName = contentFileNameProjection.getContentFileName();
        return getBlogPostContentByKey(contentFileName);
    }

    private byte[] getBlogPostContentByKey(String contentFileName) {
        return fileStorageService.downloadFile("contentfarmblogpost", MessageFormat.format("{0}/{1}", "blog-post-content", contentFileName));
    }
}
