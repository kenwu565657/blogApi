package com.contentfarm.web.controller.blogpost;

import com.contentfarm.exception.NotFoundByIdException;
import com.contentfarm.inputport.blogpost.web.IBlogPostWebDomainService;
import com.contentfarm.response.BlogPostSearchResultDto;
import com.contentfarm.response.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/blogpost")
public class BlogPostQueryController {
    private final IBlogPostWebDomainService blogPostWebDomainService;
    private final BlogPostMapper blogPostMapper;

    @GetMapping(path = "/{blogpostId}", produces = "application/json")
    public BlogPostSummaryDto getBlogPostSummaryById(@PathVariable("blogpostId") String blogpostId) {
        var blogPostDomainModel = blogPostWebDomainService.getBlogPostById(blogpostId);
        if (null == blogPostDomainModel) {
            throw NotFoundByIdException.of(blogpostId);
        }
        return blogPostMapper.toBlogPostSummaryDto(blogPostDomainModel);
    }

    @GetMapping(path = "/{blogpostId}/content/markdown", produces = "text/markdown")
    public byte[] getBlogPostContentAsMarkdownFileByBlogPostId(@PathVariable("blogpostId") String blogpostId) {
        return blogPostWebDomainService.getBlogPostContentAsMarkdownById(blogpostId);
    }

    @GetMapping(path = "/tag/list", produces = "application/json")
    public List<String> findTagList() {
        return blogPostWebDomainService.findTagList();
    }

    @GetMapping(value = "/search/{blogPostId}", produces = "application/json")
    public BlogPostSearchResultDto searchBlogPostById(@PathVariable String blogPostId) {
        return blogPostWebDomainService.searchBlogPostById(blogPostId);
    }

    @GetMapping(value = "/search", produces = "application/json", params = {"keyword"})
    public SearchResult<BlogPostSearchResultDto> searchBlogPostByKeywordAndPageNumberAndPageSize(@RequestParam("keyword") String keyword,
                                                                                                 @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return blogPostWebDomainService.searchBlogPostByKeywordAndPageNumberAndPageSize(keyword, pageNumber, pageSize);
    }

    @GetMapping(value = "/search", produces = "application/json", params = {"tagList"})
    public SearchResult<BlogPostSearchResultDto> searchBlogPostByTagList(@RequestParam List<String> tagList) {
        if (tagList == null || tagList.isEmpty()) {
            return blogPostWebDomainService.searchAllBlogPost();
        }
        return blogPostWebDomainService.searchBlogPostByTagList(tagList);
    }

    @GetMapping(value = "/search", produces = "application/json")
    public SearchResult<BlogPostSearchResultDto> searchAllBlogPost() {
        return blogPostWebDomainService.searchAllBlogPost();
    }
}
