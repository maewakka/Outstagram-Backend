package com.woo.outstagram.service;

import com.woo.outstagram.dto.profile.ProfileCountResponseDto;
import com.woo.outstagram.dto.profile.ProfileUpdateRequestDto;
import com.woo.outstagram.dto.profile.UpdatePasswordRequestDto;
import com.woo.outstagram.dto.user.UserDetailResponseDto;
import com.woo.outstagram.entity.user.User;
import com.woo.outstagram.repository.follow.FollowRepository;
import com.woo.outstagram.repository.post.PostRepository;
import com.woo.outstagram.repository.user.UserRepository;
import com.woo.outstagram.util.file.FileUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileService {

    private final FollowRepository followRepository;
    private final PostRepository postRepository;
    private final FileUploader fileUploader;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 세부사항을 반환해주는 로직
     * @param user
     */
    @Transactional
    public UserDetailResponseDto getProfile(User user) {
        return UserDetailResponseDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileImgUrl())
                .phone(user.getPhone())
                .introduce(user.getIntroduce())
                .gender(user.getGender())
                .build();
    }

    /**
     * 프로필 목록에 포함되는 여러 Count 수(게시글 수, 팔로워, 팔로잉 수)를 반환해주는 로직
     * @param user
     */
    @Transactional
    public ProfileCountResponseDto getProfileCount(User user) {

        return ProfileCountResponseDto.builder()
                .board(postRepository.countByUser(user))
                .follower(followRepository.countByFollowing(user))
                .follow(followRepository.countByFollower(user))
                .build();
    }

    /**
     * 프로필 섬네일 사진을 변경해주는 로직
     * @param user, file
     */
    @Transactional
    public UserDetailResponseDto updateProfileThumbnail(User user, MultipartFile file) {

        String dbPathLocation = "/static/profileImage/" + user.getEmail() + "/"  + file.getOriginalFilename();

        user.setProfileImgUrl(dbPathLocation);
        userRepository.save(user);
        fileUploader.saveFile(user.getEmail(), "profileImage", file);

        return this.getProfile(user);
    }

    /**
     * 유저 세부사항을 업데이트 해주는 로직
     * @param user, ProfileUpdateRequestDto
     */
    @Transactional
    public UserDetailResponseDto updateProfiles(User user, ProfileUpdateRequestDto requestDto) {

        user.setNickname(requestDto.getNickname());
        user.setIntroduce(requestDto.getIntroduce());
        user.setPhone(requestDto.getPhone());
        user.setGender(requestDto.getGender());

        userRepository.save(user);

        return this.getProfile(user);
    }

    /**
     * 유저 비밀번호 변경 로직
     * @param user, UpdatePasswordRequestDto
     */
    @Transactional
    public void updateUserPassword(User user, UpdatePasswordRequestDto requestDto) throws Exception {
        if(passwordEncoder.matches(requestDto.getPrevPw(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(requestDto.getChangePw()));
            userRepository.save(user);
        }
        else {
            throw new Exception("현재 비밀번호가 올바르지 않습니다.");
        }
    }
}
