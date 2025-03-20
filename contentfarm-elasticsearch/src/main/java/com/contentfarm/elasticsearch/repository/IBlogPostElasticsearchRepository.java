package com.contentfarm.elasticsearch.repository;

import com.contentfarm.elasticsearch.document.BlogPostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogPostElasticsearchRepository extends ElasticsearchRepository<BlogPostDocument, String> {
}
