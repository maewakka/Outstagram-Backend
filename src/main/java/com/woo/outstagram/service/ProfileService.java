package com.woo.outstagram.service;

import com.woo.outstagram.dto.user.UserDetailResponseDto;
import com.woo.outstagram.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileService {

    @Transactional
    public UserDetailResponseDto getProfile(User user) {
        return UserDetailResponseDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileImgUrl())
                .phone(user.getPhone())
                .introduce(user.getIntroduce())
                .gender(user.getGender())
                .build();
    }
}
