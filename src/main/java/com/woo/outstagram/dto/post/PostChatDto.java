package com.woo.outstagram.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woo.outstagram.entity.post.PostChat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class PostChatDto {

    private Long chatId;
    private String email;
    private String nickname;
    private String profileUrl;
    private String content;
    private LocalDateTime createdDate;

    @Builder
    public PostChatDto(Long chatId, String email, String nickname, String profileUrl, String content, LocalDateTime createdDate) {
        this.chatId = chatId;
        this.email = email;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.content = content;
        this.createdDate = createdDate;
    }

    public static PostChatDto toDto(PostChat postChat) {
        return PostChatDto.builder()
                .chatId(postChat.getId())
                .email(postChat.getUser().getEmail())
                .nickname(postChat.getUser().getNickname())
                .profileUrl(postChat.getUser().getProfileImgUrl())
                .content(postChat.getContent())
                .createdDate(postChat.getCreatedDate())
                .build();
    }
}
