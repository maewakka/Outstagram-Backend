package com.woo.outstagram.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woo.outstagram.entity.post.Post;
import com.woo.outstagram.entity.user.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import static com.woo.outstagram.entity.post.QPost.post;

import java.util.List;

@Repository
public class PostRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public PostRepositorySupport(JPAQueryFactory queryFactory) {
        super(Post.class);
        this.queryFactory = queryFactory;
    }

    public List<Post> getPosts(List<User> userList) {

        return queryFactory.selectFrom(post).where(post.user.in(userList)).orderBy(post.modifiedDate.desc()).fetch();
    }
}