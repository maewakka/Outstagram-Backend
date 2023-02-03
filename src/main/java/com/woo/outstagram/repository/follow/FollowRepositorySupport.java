package com.woo.outstagram.repository.follow;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woo.outstagram.entity.follow.Follow;
import com.woo.outstagram.entity.user.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.woo.outstagram.entity.follow.QFollow.follow;

@Repository
public class FollowRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public FollowRepositorySupport(JPAQueryFactory queryFactory) {
        super(Follow.class);
        this.queryFactory = queryFactory;
    }

    public List<User> followerList(User follower) {
        return queryFactory.select(follow.following).from(follow).where(follow.follower.eq(follower)).fetch();
    }

}
