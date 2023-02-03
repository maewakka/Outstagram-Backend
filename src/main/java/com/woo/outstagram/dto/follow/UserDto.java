package com.woo.outstagram.dto.follow;

import com.woo.outstagram.entity.user.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {

    private String email;
    private String nickname;
    private String profileUrl;
    private boolean isFollow;

    @Builder
    public UserDto(String email, String nickname, String profileUrl, boolean isFollow) {
        this.email = email;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.isFollow = isFollow;
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileImgUrl())
                .build();
    }
}
