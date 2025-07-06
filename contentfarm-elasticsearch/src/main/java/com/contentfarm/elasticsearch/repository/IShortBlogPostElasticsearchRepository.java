package com.contentfarm.elasticsearch.repository;

import com.contentfarm.elasticsearch.document.ShortBlogPostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface IShortBlogPostElasticsearchRepository extends ElasticsearchRepository<ShortBlogPostDocument, String> {

}
