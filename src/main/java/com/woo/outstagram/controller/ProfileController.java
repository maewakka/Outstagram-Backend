package com.woo.outstagram.controller;

import com.woo.outstagram.entity.user.CurrentUser;
import com.woo.outstagram.entity.user.User;
import com.woo.outstagram.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile")
    public ResponseEntity getProfile(@CurrentUser User user) {
        try {
            return ResponseEntity.ok().body(profileService.getProfile(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("요청에 오류가 발생하였습니다.");
        }
    }


}
