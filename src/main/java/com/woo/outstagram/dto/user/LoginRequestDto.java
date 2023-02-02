package com.woo.outstagram.dto.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDto {

    @NotBlank(message = "빈 항목이 존재합니다.")
    @Email(message = "이메일 양식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "빈 항목이 존재합니다.")
    private String password;

    @Builder
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
