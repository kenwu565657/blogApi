package com.contentfarm.elasticsearch.service;

import com.contentfarm.elasticsearch.TestContainerElasticsearch;
import com.contentfarm.elasticsearch.document.BlogPostDocument;
import com.contentfarm.elasticsearch.exception.DocumentNotFoundException;
import com.contentfarm.elasticsearch.repository.IBlogPostElasticsearchRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;

import java.text.MessageFormat;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ImportTestcontainers(TestContainerElasticsearch.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TestConfiguration.class)
class BlogPostSearchServiceTest {

    private static final Logger log = LoggerFactory.getLogger(BlogPostSearchServiceTest.class);

    @Autowired
    BlogPostSearchService blogPostSearchService;

    @Autowired
    IBlogPostElasticsearchRepository blogPostElasticsearchRepository;

    @Autowired
    private ElasticsearchTemplate template;

    @BeforeAll
    void setUp() {
        var testingDocument = findTestingBlogPostDocument();
        blogPostElasticsearchRepository.deleteAll();
        blogPostElasticsearchRepository.saveAll(testingDocument);
        template.indexOps(BlogPostDocument.class).refresh();
    }

    @Test
    void searchAllBlogPost() {
        var result = blogPostSearchService.searchAllBlogPost();
        Assertions.assertEquals(findTestingBlogPostDocument().size(), result.getSearchResultItemList().size());
    }

    @Test
    void getBlogPostById() {
        var result1 = blogPostSearchService.getBlogPostById("testingId1");
        Assertions.assertNotNull(result1);
        Assertions.assertEquals("testingTitle1", result1.getTitle());
        log.info(() -> MessageFormat.format("The Result: {0}", result1));

        var result2 = blogPostSearchService.getBlogPostById("testingId2");
        Assertions.assertNotNull(result2);
        Assertions.assertEquals("testingTitle2", result2.getTitle());
        log.info(() -> MessageFormat.format("The Result: {0}", result2));

        var result3 = blogPostSearchService.getBlogPostById("testingId3");
        Assertions.assertNotNull(result3);
        Assertions.assertEquals("testingTitle3", result3.getTitle());
        log.info(() -> MessageFormat.format("The Result: {0}", result3));
    }

    @Test
    void getBlogPostById_invalidId() {
        var throwable = Assertions.assertThrows(DocumentNotFoundException.class, () ->  blogPostSearchService.getBlogPostById("invalidId"));
        Assertions.assertEquals("Document not found.", throwable.getMessage());
    }

    @Test
    void searchBlogPostByKeywordAndPageNumberAndPageSize() {
        var result0 = blogPostSearchService.searchBlogPostByKeywordAndPageNumberAndPageSize("nonKeyword", 0, 1);
        Assertions.assertEquals(0, result0.getSearchResultItemList().size());

        var result1_0 = blogPostSearchService.searchBlogPostByKeywordAndPageNumberAndPageSize("keyword", 0, 3);
        Assertions.assertEquals(3, result1_0.getSearchResultItemList().size());
        Assertions.assertEquals(3, result1_0.getSearchResultCount());

        var result1_1 = blogPostSearchService.searchBlogPostByKeywordAndPageNumberAndPageSize("keyword", 0, 1);
        Assertions.assertEquals(1, result1_1.getSearchResultItemList().size());
        Assertions.assertEquals(3, result1_1.getSearchResultCount());

        var result1_2 = blogPostSearchService.searchBlogPostByKeywordAndPageNumberAndPageSize("keyword", 1, 1);
        Assertions.assertEquals(1, result1_2.getSearchResultItemList().size());
        Assertions.assertEquals(3, result1_2.getSearchResultCount());

        var result1_3 = blogPostSearchService.searchBlogPostByKeywordAndPageNumberAndPageSize("keyword", 2, 1);
        Assertions.assertEquals(1, result1_3.getSearchResultItemList().size());
        Assertions.assertEquals(3, result1_3.getSearchResultCount());

        var result1_4 = blogPostSearchService.searchBlogPostByKeywordAndPageNumberAndPageSize("keyword", 3, 1);
        Assertions.assertEquals(0, result1_4.getSearchResultItemList().size());
        Assertions.assertEquals(3, result1_4.getSearchResultCount());

        var result2_0 = blogPostSearchService.searchBlogPostByKeywordAndPageNumberAndPageSize("testingSummary1", 0, 3);
        Assertions.assertEquals(1, result2_0.getSearchResultItemList().size());
        Assertions.assertEquals(1, result2_0.getSearchResultCount());

        var result2_1 = blogPostSearchService.searchBlogPostByKeywordAndPageNumberAndPageSize("testingSummary1", 1, 3);
        Assertions.assertEquals(0, result2_1.getSearchResultItemList().size());
        Assertions.assertEquals(1, result2_1.getSearchResultCount());

        var result3 = blogPostSearchService.searchBlogPostByKeywordAndPageNumberAndPageSize("testingSummary2", 0, 3);
        Assertions.assertEquals(1, result3.getSearchResultItemList().size());
        Assertions.assertEquals(1, result3.getSearchResultCount());

        var result4 = blogPostSearchService.searchBlogPostByKeywordAndPageNumberAndPageSize("testingSummary3",0, 3);
        Assertions.assertEquals(1, result4.getSearchResultItemList().size());
        Assertions.assertEquals(1, result4.getSearchResultCount());

        var result5 = blogPostSearchService.searchBlogPostByKeywordAndPageNumberAndPageSize("testingTitle1", 0, 3);
        Assertions.assertEquals(1, result5.getSearchResultItemList().size());
        Assertions.assertEquals(1, result5.getSearchResultCount());

        var result6 = blogPostSearchService.searchBlogPostByKeywordAndPageNumberAndPageSize("testingTitle2", 0, 3);
        Assertions.assertEquals(1, result6.getSearchResultItemList().size());
        Assertions.assertEquals(1, result6.getSearchResultCount());

        var result7 = blogPostSearchService.searchBlogPostByKeywordAndPageNumberAndPageSize("testingTitle3", 0, 3);
        Assertions.assertEquals(1, result7.getSearchResultItemList().size());
        Assertions.assertEquals(1, result7.getSearchResultCount());
    }

    @Test
    void searchBlogPostByTagList() {
        List<String> tagList0 = List.of();
        var result0 = blogPostSearchService.searchBlogPostByTagList(tagList0);
        Assertions.assertEquals(findTestingBlogPostDocument().size(), result0.getSearchResultItemList().size());
        for (var searchHit : result0.getSearchResultItemList()) {
            log.info(() -> MessageFormat.format("The Result: {0}", searchHit.toString()));
        }

        var tagList1 = List.of("tag1", "tag2");
        var result1 = blogPostSearchService.searchBlogPostByTagList(tagList1);
        Assertions.assertEquals(0, result1.getSearchResultItemList().size());

        var tagList2 = List.of("Java");
        var result2 = blogPostSearchService.searchBlogPostByTagList(tagList2);
        Assertions.assertEquals(0, result2.getSearchResultItemList().size());

        var tagList3 = List.of("testingTag");
        var result3 = blogPostSearchService.searchBlogPostByTagList(tagList3);
        Assertions.assertEquals(0, result3.getSearchResultItemList().size());

        var tagList4 = List.of("testingTag1");
        var result4 = blogPostSearchService.searchBlogPostByTagList(tagList4);
        Assertions.assertEquals(2, result4.getSearchResultItemList().size());

        var tagList5 = List.of("testingTag2");
        var result5 = blogPostSearchService.searchBlogPostByTagList(tagList5);
        Assertions.assertEquals(2, result5.getSearchResultItemList().size());

        var tagList6 = List.of("testingTag1", "testingTag2");
        var result6 = blogPostSearchService.searchBlogPostByTagList(tagList6);
        Assertions.assertEquals(2, result6.getSearchResultItemList().size());
    }

    private List<BlogPostDocument> findTestingBlogPostDocument() {
        var testingBlogPostDocument1 = buildNormalTestBlogPostDocument(1);
        var testingBlogPostDocument2 = buildNormalTestBlogPostDocument(2);
        var testingBlogPostDocument3 = buildNormalTestBlogPostDocument(3);
        testingBlogPostDocument3.setTagList(List.of());
        return List.of(
                testingBlogPostDocument1,
                testingBlogPostDocument2,
                testingBlogPostDocument3
        );
    }

    private BlogPostDocument buildNormalTestBlogPostDocument(int orderNumber) {
        return BlogPostDocument
                .builder()
                .id(MessageFormat.format("testingId{0}", orderNumber))
                .title(MessageFormat.format("testingTitle{0}", orderNumber))
                .tagList(List.of("testingTag1", "testingTag2"))
                .summary(MessageFormat.format("This is a testingSummary{0} keyword", orderNumber))
                .build();
    }
}