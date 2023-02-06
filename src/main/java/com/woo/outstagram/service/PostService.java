package com.woo.outstagram.service;

import com.woo.outstagram.dto.follow.UserDto;
import com.woo.outstagram.dto.post.*;
import com.woo.outstagram.entity.chat.Chat;
import com.woo.outstagram.entity.post.Post;
import com.woo.outstagram.entity.post.PostChat;
import com.woo.outstagram.entity.post.PostFile;
import com.woo.outstagram.entity.post.PostLike;
import com.woo.outstagram.entity.user.User;
import com.woo.outstagram.repository.follow.FollowRepositorySupport;
import com.woo.outstagram.repository.post.*;
import com.woo.outstagram.util.file.FileUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostRepositorySupport postRepositorySupport;
    private final PostFileRepository postFileRepository;
    private final FileUploader fileUploader;
    private final FollowRepositorySupport followRepositorySupport;
    private final PostLikeRepository postLikeRepository;
    private final PostChatRepository postChatRepository;

    @Transactional
    public void uploadPost(User user, UploadPostRequestDto requestDto) {

        List<MultipartFile> files = requestDto.getFile();

        // POST Entity 생성 및 저장
        Post savedPost = Post
                .builder()
                .content(requestDto.getContent())
                .user(user)
                .build();
        postRepository.save(savedPost);

        // POST Image Entity 생성 및 저장
        AtomicInteger index = new AtomicInteger();
        files.stream()
                .forEach((file) -> {
                    String dbPathLocation = "/static/post/" + user.getEmail() + "/" + savedPost.getId() + "/" + file.getOriginalFilename();

                    PostFile savePostFile = PostFile.builder()
                                    .postFileIndex((long) index.getAndIncrement())
                                    .postFileUrl(dbPathLocation)
                                    .post(savedPost)
                                    .user(user)
                                    .build();

                    fileUploader.saveFile(user.getEmail(), savedPost.getId(), "post", file);
                    postFileRepository.save(savePostFile);
                });
    }

    @Transactional
    public PostResponseDto getPostList(User follower) {

        // 게시글을 가져오기 위한 유저 목록 리스트(본인 + 팔로잉 인원)
        List<User> userList = new ArrayList<>();
        userList.add(follower);

        List<User> followingUserList = followRepositorySupport.followerList(follower);
        followingUserList.forEach((user) -> userList.add(user));

        // 해당 유저의 post와 postFile을 가져와 postList에 삽입한다.
        List<PostDto> postDtoList = new ArrayList<>();

        // 유저 리스트에 포함된 유저들의 모든 게시글을 수정시간 순으로 정렬하여 가져온다.
        List<Post> postList = postRepositorySupport.getPosts(userList);

        // 게시글에 대한 처리
        postList.forEach((post) -> {
            List<PostFile> postFileList = postFileRepository.findAllByPost(post);
            List<PostFileDto> postFileDtoList = new ArrayList<>();

            // PostFileDto로 변환
            postFileList.forEach((postFile) -> {
                postFileDtoList.add(PostFileDto.toDto(postFile));
            });

            // PostDtoList에 내용 삽입
            postDtoList.add(
                    PostDto.builder()
                            .postFileList(postFileDtoList)
                            .postId(post.getId())
                            .content(post.getContent())
                            .user(UserDto.toDto(post.getUser()))
                            .like(postLikeRepository.existsByPostAndUser(post, follower))
                            .build()
            );
        });

        return PostResponseDto.builder().postList(postDtoList).build();
    }

    @Transactional
    public PostResponseDto getMyPostList(User user) {
        List<Post> postList = postRepository.findAllByUserOrderByModifiedDateDesc(user);
        List<PostDto> postDtoList = new ArrayList<>();

        postList.forEach((post -> {
            List<PostFile> postFileList = postFileRepository.findAllByPost(post);
            List<PostFileDto> postFileDtoList = new ArrayList<>();

            // PostFileDto로 변환
            postFileList.forEach((postFile) -> {
                postFileDtoList.add(PostFileDto.toDto(postFile));
            });

            // PostDtoList에 내용 삽입
            postDtoList.add(
                    PostDto.builder()
                            .postFileList(postFileDtoList)
                            .postId(post.getId())
                            .content(post.getContent())
                            .user(UserDto.toDto(post.getUser()))
                            .like(postLikeRepository.existsByPostAndUser(post, user))
                            .build()
            );
        }));

        return PostResponseDto.builder().postList(postDtoList).build();
    }

    @Transactional
    public PostResponseDto deletePost(User user, Long postId) {
        Post savedPost = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다."));
        List<PostFile> postFileList = postFileRepository.findAllByPost(savedPost);
        List<PostChat> postChatList = postChatRepository.findAllByPostOrderByCreatedDateDesc(savedPost);

        postFileList.forEach((postFile -> {
            postFileRepository.delete(postFile);
        }));

        postChatList.forEach((postChat -> {
            postChatRepository.delete(postChat);
        }));

        postRepository.delete(savedPost);

        return this.getMyPostList(user);
    }


    @Transactional
    public PostResponseDto setPostLike(User user, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다."));

        // postLike 저장 DB에 요청 좋아요 정보가 없다면, 저장한다.
        if(!postLikeRepository.existsByPostAndUser(post, user)) {
            postLikeRepository.save(PostLike.builder()
                    .post(post)
                    .user(user)
                    .build());
        }

        return this.getPostList(user);
    }

    @Transactional
    public PostResponseDto deletePostLike(User user, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다."));

        PostLike postLike = postLikeRepository.findByPostAndUser(post, user).orElseThrow(() -> new EntityNotFoundException());

        postLikeRepository.delete(postLike);

        return this.getPostList(user);
    }

    @Transactional
    public PostChatResponseDto getPostChatList(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다."));

        List<PostChat> postChatList = postChatRepository.findAllByPostOrderByCreatedDateDesc(post);
        List<PostChatDto> postChatDtoList = new ArrayList<>();

        postChatList.forEach((postChat -> {
            postChatDtoList.add(PostChatDto.toDto(postChat));
        }));

        return PostChatResponseDto.builder().postChatList(postChatDtoList).build();
    }

    @Transactional
    public PostChatResponseDto setPostChat(User user, PostChatRequestDto requestDto) {
        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(() -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다."));

        postChatRepository.save(PostChat.builder()
                .post(post)
                .user(user)
                .content(requestDto.getContent())
                .build());

        return this.getPostChatList(requestDto.getPostId());
    }

    @Transactional
    public PostChatResponseDto deletePostChat(Long postId, Long postChatId) {
        PostChat postChat = postChatRepository.findById(postChatId).orElseThrow(() -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다."));

        postChatRepository.delete(postChat);

        return this.getPostChatList(postId);
    }

}
