package com.woo.outstagram.repository.post;

import com.woo.outstagram.entity.post.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFileRepository extends JpaRepository<PostFile, Long> {
}
