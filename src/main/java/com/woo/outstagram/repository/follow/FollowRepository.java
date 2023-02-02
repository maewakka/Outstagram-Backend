package com.woo.outstagram.repository.follow;

import com.woo.outstagram.entity.follow.Follow;
import com.woo.outstagram.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowing(User follower, User following);
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
}
