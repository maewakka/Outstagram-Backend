package com.woo.outstagram.config;

import com.woo.outstagram.config.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Secrutiy 관련 설정들
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    // Security를 거치지 않는 URI 목록(Swagger, Socket 등)
    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**",
            /* Auth */
            "/users/**",
            "/static/**",
            "/socket/**"
    };

    /**
     * 비밀번호 암호화를 위한 BCryptPasswordEncoder를 Bean에 등록
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Security 세부 항목 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                    // Security에서 제공하는 기본 httpBasic 및 crsf, cors 기능 비활성화
                    .httpBasic().disable()
                    .csrf().disable()
                    .cors()
                .and()
                    // Permit URL 리스트만 허용
                    .authorizeRequests()
                    .antMatchers(PERMIT_URL_ARRAY).permitAll()
                .and()
                    // JWT 토큰을 사용하기 때문에 세션은 Stateless로 설정
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    // JWT 필터를 UsernamePasswordAuthenticationFilter의 이전에 끼워넣는다.
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

}
