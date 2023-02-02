package com.woo.outstagram.dto.user;

import com.woo.outstagram.entity.user.Role;
import com.woo.outstagram.entity.user.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignUpRequestDto {

    @Email(message = "이메일 양식이 올바르지 않습니다.")
    @NotBlank(message = "빈 항목이 존재합니다.")
    private String email;

    @NotBlank(message = "빈 항목이 존재합니다.")
    private String password;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식이 올바르지 않습니다. 01x-xxx(x)-xxxx")
    @NotBlank(message = "빈 항목이 존재합니다.")
    private String phone;

    @NotBlank(message = "빈 항목이 존재합니다.")
    private String name;

    @NotBlank(message = "빈 항목이 존재합니다.")
    private String nickname;

    @Builder
    public SignUpRequestDto(String email, String password, String name, String nickname, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
    }

    /**
     *
     * @param password
     * @return
     */
    public User toEntity(String password) {
        return User.builder()
                .email(this.email)
                .password(password)
                .name(this.name)
                .nickname(this.nickname)
                .phone(this.phone)
                .profileImgUrl("/static/profileImage/default_profile.png")
                .role(Role.ROLE_USER)
                .gender("")
                .introduce("")
                .build();
    }
}
