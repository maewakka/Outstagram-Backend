package com.woo.outstagram.service;

import com.woo.outstagram.dto.post.UploadPostRequestDto;
import com.woo.outstagram.entity.post.Post;
import com.woo.outstagram.entity.post.PostFile;
import com.woo.outstagram.entity.user.User;
import com.woo.outstagram.repository.post.PostFileRepository;
import com.woo.outstagram.repository.post.PostRepository;
import com.woo.outstagram.util.file.FileUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;
    private final FileUploader fileUploader;

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
                    String dbPathLocation = "/post/" + user.getEmail() + "/" + savedPost.getId() + "/" + file.getOriginalFilename();

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

}
