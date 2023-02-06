package com.woo.outstagram.repository.post;

import com.woo.outstagram.entity.post.Post;
import com.woo.outstagram.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserOrderByModifiedDateDesc(User user);
    Long countByUser(User user);
}
