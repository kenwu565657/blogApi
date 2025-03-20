package com.contentfarm.persistence.blogpost.dao;

import com.contentfarm.persistence.blogpost.entity.BlogPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogPostDao extends JpaRepository<BlogPostEntity, String>, JpaSpecificationExecutor<BlogPostEntity> {
    ContentFileNameProjection getContentFileNameById(String id);

    interface ContentFileNameProjection {
        String getContentFileName();
    }
}
