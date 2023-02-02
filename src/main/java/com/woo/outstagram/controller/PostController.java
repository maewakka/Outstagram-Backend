package com.woo.outstagram.controller;

import com.woo.outstagram.dto.post.UploadPostRequestDto;
import com.woo.outstagram.entity.user.CurrentUser;
import com.woo.outstagram.entity.user.User;
import com.woo.outstagram.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
