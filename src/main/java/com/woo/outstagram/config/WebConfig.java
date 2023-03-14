package com.woo.outstagram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 관련 설정
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${path.connectUrl}")
    private String connectUrl;
    @Value("${path.repositoryPath}")
    private String repositoryPath;

    /**
     * 들어오는 URL을 로컬 폴더에 있는 파일로 연결해준다.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(connectUrl)
                .addResourceLocations("file:" + repositoryPath);
    }

}
