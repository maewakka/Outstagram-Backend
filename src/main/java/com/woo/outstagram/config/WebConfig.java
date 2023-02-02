package com.woo.outstagram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${path.connectUrl}")
    private String connectUrl;
    @Value("${path.repositoryPath}")
    private String repositoryPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(connectUrl)
                .addResourceLocations("file:" + repositoryPath);
    }

}
