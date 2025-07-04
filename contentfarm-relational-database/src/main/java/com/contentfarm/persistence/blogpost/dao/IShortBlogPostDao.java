package com.contentfarm.persistence.blogpost.dao;

import com.contentfarm.persistence.blogpost.entity.ShortBlogPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IShortBlogPostDao extends JpaRepository<ShortBlogPostEntity, String>, JpaSpecificationExecutor<ShortBlogPostEntity> {}
