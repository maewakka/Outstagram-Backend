package com.woo.outstagram.repository.post;

import com.woo.outstagram.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
