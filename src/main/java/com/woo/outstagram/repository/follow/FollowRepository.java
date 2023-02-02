package com.woo.outstagram.repository.follow;

import com.woo.outstagram.entity.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
