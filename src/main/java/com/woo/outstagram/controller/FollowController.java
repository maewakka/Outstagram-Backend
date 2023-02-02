package com.woo.outstagram.controller;

import com.woo.outstagram.entity.user.CurrentUser;
import com.woo.outstagram.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FollowController {

    @GetMapping("/follow")
    public String follow(@CurrentUser User user) {
        log.info(user.toString());

        return user.toString();
    }
}
