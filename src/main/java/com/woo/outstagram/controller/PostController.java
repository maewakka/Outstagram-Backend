package com.woo.outstagram.controller;

import com.woo.outstagram.dto.post.UploadPostRequestDto;
import com.woo.outstagram.entity.user.CurrentUser;
import com.woo.outstagram.entity.user.User;
import com.woo.outstagram.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity uploadPost(@CurrentUser User user, @Valid @ModelAttribute UploadPostRequestDto requestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();

            return ResponseEntity.badRequest().body(errorMsg);
        }

        try {
            postService.uploadPost(user, requestDto);

            return ResponseEntity.ok().body("게시물이 성공적으로 업로드 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/post-list")
    public ResponseEntity getPostList(@CurrentUser User user) {
        try {
            return ResponseEntity.ok().body(postService.getPostList(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/like")
    public ResponseEntity setPostLike(@CurrentUser User user, @RequestParam(value = "postId") Long postId) {
        try {
            return ResponseEntity.ok().body(postService.setPostLike(user, postId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/like")
    public ResponseEntity deletePostLike(@CurrentUser User user, @RequestParam(value = "postId") Long postId) {
        try {
            return ResponseEntity.ok().body(postService.deletePostLike(user, postId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
