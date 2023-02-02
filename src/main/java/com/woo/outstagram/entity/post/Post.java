package com.woo.outstagram.entity.post;

import com.woo.outstagram.entity.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "made_user")
    private User user;

    private String content;

    @Builder
    public Post(User user, String content) {
        this.user = user;
        this.content = content;
    }
}
