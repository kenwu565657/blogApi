package com.contentfarm.elasticsearch.service;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import com.contentfarm.elasticsearch.document.BlogPostDocument;
import com.contentfarm.elasticsearch.exception.DocumentNotFoundException;
import com.contentfarm.elasticsearch.mapper.BlogPostSearchMapper;
import com.contentfarm.outputport.blogpost.search.IBlogPostSearchService;
import com.contentfarm.response.BlogPostSearchResultDto;
import com.contentfarm.response.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchOperations;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.Order;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightFieldParameters;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogPostSearchService implements IBlogPostSearchService {
    private final SearchOperations searchOperations;
    private final ElasticsearchOperations elasticsearchOperations;
    private final BlogPostSearchMapper blogPostSearchMapper;
    private final Pageable defaultPageable = PageRequest.of(0, 20);

    @Override
    public BlogPostSearchResultDto getBlogPostById(String id) {
        var blogPostDocument = elasticsearchOperations.get(id, BlogPostDocument.class);
        if (null == blogPostDocument) {
            throw DocumentNotFoundException.of();
        }
        return blogPostSearchMapper.toBlogPostSearchResultDto(blogPostDocument);
    }

    @Override
    public SearchResult<BlogPostSearchResultDto> searchAllBlogPost() {
        NativeQuery nativeQuery = new NativeQueryBuilder()
                .withPageable(defaultPageable)
                .build();
        SearchHits<BlogPostDocument> searchHits = searchOperations.search(nativeQuery, BlogPostDocument.class);
        return blogPostSearchMapper.toSearchResult(searchHits);
    }

    @Override
    public SearchResult<BlogPostSearchResultDto> searchBlogPostByKeywordAndPageNumberAndPageSize(String keyword, Integer pageNumber, Integer pageSize) {
        int exactPageNumber = null == pageNumber ? 0 : Math.max(0, pageNumber);
        int exactPageSize = null == pageSize ? 20 : Math.min(20, pageSize);
        Sort sort = Sort.by(new Order(Sort.Direction.DESC, "_score"));
        Pageable pageable = PageRequest.of(exactPageNumber, exactPageSize, sort);

        final HighlightFieldParameters.HighlightFieldParametersBuilder highlightFieldParametersBuilder = HighlightFieldParameters.builder();
        highlightFieldParametersBuilder.withPreTags("<font color='red'>")
                .withPostTags("</font>")
                .withRequireFieldMatch(true)
                .withNumberOfFragments(0);
        final HighlightField titleHighlightField = new HighlightField(BlogPostDocument.Fields.title, highlightFieldParametersBuilder.build());
        final HighlightField contentHighlightField = new HighlightField(BlogPostDocument.Fields.summary, highlightFieldParametersBuilder.build());
        final Highlight highLightField = new Highlight(List.of(titleHighlightField,contentHighlightField));

        MultiMatchQuery.Builder multiMatchQueryBuilder = new MultiMatchQuery.Builder();
        multiMatchQueryBuilder.query(keyword);
        multiMatchQueryBuilder.fields(List.of(BlogPostDocument.Fields.title, BlogPostDocument.Fields.summary));

        var nativeQuery = new NativeQueryBuilder()
                .withQuery(q -> q.multiMatch(multiMatchQueryBuilder.build()))
                .withPageable(pageable)
                .withHighlightQuery(new HighlightQuery(highLightField, BlogPostDocument.class))
                .build();
        SearchHits<BlogPostDocument> searchHits = elasticsearchOperations.search(nativeQuery, BlogPostDocument.class);
        return blogPostSearchMapper.toSearchResult(searchHits);
    }

    @Override
    public SearchResult<BlogPostSearchResultDto> searchBlogPostByTagList(List<String> tagList) {
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();
        boolQueryBuilder.minimumShouldMatch(String.valueOf(tagList.size()));
        List<Query> tagQueries = tagList.stream().map(p -> {
            TermQuery termQuery = new TermQuery.Builder().field(BlogPostDocument.Fields.tagList).value(p).build();
            return Query.of(q -> q.term(termQuery));
        }).toList();
        boolQueryBuilder.should(tagQueries);

        Sort sort = Sort.by(new Order(Sort.Direction.DESC, "_score"));
        Pageable pageable = PageRequest.of(0, 20, sort);

        var nativeQuery = new NativeQueryBuilder()
                .withQuery(q -> q.bool(boolQueryBuilder.build()))
                .withPageable(pageable)
                .build();
        SearchHits<BlogPostDocument> searchHits = elasticsearchOperations.search(nativeQuery, BlogPostDocument.class);
        return blogPostSearchMapper.toSearchResult(searchHits);
    }
}
