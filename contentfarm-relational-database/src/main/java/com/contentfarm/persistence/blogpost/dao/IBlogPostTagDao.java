package com.contentfarm.persistence.blogpost.dao;

import com.contentfarm.persistence.blogpost.entity.BlogPostTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IBlogPostTagDao extends JpaRepository<BlogPostTagEntity, String>, JpaSpecificationExecutor<BlogPostTagEntity> {
    List<BlogPostTagEntity> findByTagNameIn(Collection<String> tagName);
}
