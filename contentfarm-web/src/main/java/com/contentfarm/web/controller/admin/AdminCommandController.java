package com.contentfarm.web.controller.admin;

import com.contentfarm.ContentFarmLocaleDateTimeUtils;
import com.contentfarm.aggregateroot.blogpost.BlogPostDomainModel;
import com.contentfarm.command.AdminAddBlogPostTagCommand;
import com.contentfarm.command.AdminDeleteBlogPostCommand;
import com.contentfarm.command.AdminDeleteBlogPostTagCommand;
import com.contentfarm.command.AdminIndexBlogPostCommand;
import com.contentfarm.inputport.blogpost.web.IAdminBlogPostCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCommandController {
    private final IAdminBlogPostCommandService adminBlogPostCommandService;

    @PreAuthorize("hasAuthority('SCOPE_admin.write')")
    @PostMapping(path = "/blogpost", produces = "application/json")
    public ResponseEntity<Void> indexBlogPost(@RequestBody AdminIndexBlogPostCommand adminIndexBlogPostCommand) {
        var blogpostDomainModel = extractBlogPostDomainModel(adminIndexBlogPostCommand);
        try {
            adminBlogPostCommandService.indexBlogPost(blogpostDomainModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_admin.write')")
    @DeleteMapping(path = "/blogpost", produces = "application/json")
    public ResponseEntity<Void> deleteBlogPost(@RequestBody AdminDeleteBlogPostCommand adminDeleteBlogPostCommand) {
        try {
            adminBlogPostCommandService.deleteBlogPost(adminDeleteBlogPostCommand.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_admin.write')")
    @PostMapping(path = "/blogpost/tag", produces = "application/json")
    public ResponseEntity<Void> addTagList(@RequestBody AdminAddBlogPostTagCommand adminAddBlogPostTagCommand) {
        adminBlogPostCommandService.addBlogPostTag(adminAddBlogPostTagCommand.blogPostTagList());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_admin.write')")
    @DeleteMapping(path = "/blogpost/tag", produces = "application/json")
    public ResponseEntity<Void> deleteTagList(@RequestBody AdminDeleteBlogPostTagCommand adminDeleteBlogPostTagCommand) {
        adminBlogPostCommandService.deleteBlogPostTag(adminDeleteBlogPostTagCommand.blogPostTagList());
        return ResponseEntity.noContent().build();
    }

    private BlogPostDomainModel extractBlogPostDomainModel(AdminIndexBlogPostCommand adminIndexBlogPostCommand) {
        BlogPostDomainModel blogPostDomainModel = new BlogPostDomainModel();
        blogPostDomainModel.setId(adminIndexBlogPostCommand.getId());
        blogPostDomainModel.setTitle(adminIndexBlogPostCommand.getTitle());
        blogPostDomainModel.setTagList(adminIndexBlogPostCommand.getTagList());
        blogPostDomainModel.setSummary(adminIndexBlogPostCommand.getSummary());
        blogPostDomainModel.setAuthorId(adminIndexBlogPostCommand.getAuthorName());
        blogPostDomainModel.setCreatedDateTime(null == adminIndexBlogPostCommand.getPostDate() ? ContentFarmLocaleDateTimeUtils.ofNow() : adminIndexBlogPostCommand.getPostDate());
        blogPostDomainModel.setCoverImageUrl(adminIndexBlogPostCommand.getCoverImageUrl());
        blogPostDomainModel.setContentType(adminIndexBlogPostCommand.getContentType());
        blogPostDomainModel.setContentFileName(adminIndexBlogPostCommand.getContentFileName());
        return blogPostDomainModel;
    }
}
