package com.woo.outstagram.entity.post;

import com.woo.outstagram.entity.BaseTimeEntity;
import com.woo.outstagram.entity.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class PostChat extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_chat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    @Builder
    public PostChat(Post post, User user, String content) {
        this.post = post;
        this.user = user;
        this.content = content;
    }
}
