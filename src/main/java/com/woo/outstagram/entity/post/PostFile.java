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
public class PostFile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_file_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "post_file_index")
    private Long postFileIndex;

    @Column(name = "post_file_url")
    private String postFileUrl;

    @Builder
    public PostFile(Post post, User user, Long postFileIndex, String postFileUrl) {
        this.post = post;
        this.user = user;
        this.postFileIndex = postFileIndex;
        this.postFileUrl = postFileUrl;
    }
}
