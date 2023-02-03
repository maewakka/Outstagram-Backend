package com.woo.outstagram.dto.post;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class PostChatDto {

    private String nickname;
    private String profileUrl;
    private String content;
    private Date modifiedDate;

    @Builder
    public PostChatDto(String nickname, String profileUrl, String content, Date modifiedDate) {
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.content = content;
        this.modifiedDate = modifiedDate;
    }
}
