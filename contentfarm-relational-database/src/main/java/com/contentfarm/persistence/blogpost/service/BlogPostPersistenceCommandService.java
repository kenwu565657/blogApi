package com.contentfarm.persistence.blogpost.service;

import com.contentfarm.ContentFarmLocaleDateTimeUtils;
import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;
import com.contentfarm.persistence.blogpost.dao.IBlogPostDao;
import com.contentfarm.persistence.blogpost.dao.IBlogPostTagDao;
import com.contentfarm.persistence.blogpost.entity.BlogPostTagEntity;
import com.contentfarm.persistence.blogpost.mapper.BlogPostEntityMapper;
import com.contentfarm.outputport.blogpost.persistence.IBlogPostPersistenceCommandService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlogPostPersistenceCommandService implements IBlogPostPersistenceCommandService {
    private final IBlogPostDao blogPostDao;
    private final IBlogPostTagDao blogPostTagDao;
    private final BlogPostEntityMapper blogPostEntityMapper;

    @Override
    public BlogPostDomainModel upsertBlogPost(BlogPostDomainModel blogPostDomainModel) {
        var blogPostEntity = blogPostEntityMapper.mapToBlogPostEntity(blogPostDomainModel);
        if (null == blogPostEntity.getId()) {
            String uuid = UUID.randomUUID().toString();
            blogPostEntity.setId(uuid);
            blogPostDomainModel.setId(uuid);
        } else {
            blogPostEntity.setLastUpdatedDateTime(ContentFarmLocaleDateTimeUtils.ofNow());
        }
        blogPostDao.save(blogPostEntity);
        return blogPostDomainModel;
    }

    @Override
    public String deleteBlogPostById(String id) {
        blogPostDao.deleteById(id);
        return id;
    }

    @Override
    @Transactional
    public void addBlogPostTag(List<String> blogPostTagNameList) {
        if (blogPostTagNameList == null || blogPostTagNameList.isEmpty()) {
            return;
        }
        var blogPostTagEntityList = buildBlogPostTagEntityList(blogPostTagNameList);
        blogPostTagDao.saveAll(blogPostTagEntityList);
    }

    @Override
    @Transactional
    public void deleteBlogPostTag(List<String> blogPostTagNameList) {
        if (blogPostTagNameList == null || blogPostTagNameList.isEmpty()) {
            return;
        }
        var blogPostTagEntityList = blogPostTagDao.findByTagNameIn(blogPostTagNameList);
        blogPostTagDao.deleteAll(blogPostTagEntityList);
    }

    private List<BlogPostTagEntity> buildBlogPostTagEntityList(List<String> blogPostTagNameList) {
        return blogPostTagNameList.stream().map(this::buildBlogPostTagEntity).toList();
    }

    private BlogPostTagEntity buildBlogPostTagEntity(String blogPostTagName) {
        return BlogPostTagEntity
                .builder()
                .id(UUID.randomUUID().toString())
                .tagName(blogPostTagName)
                .build();
    }
}
