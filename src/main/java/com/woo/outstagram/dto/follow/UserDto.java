package com.woo.outstagram.dto.follow;

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
}
