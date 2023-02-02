package com.woo.outstagram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {
        "com.woo.outstagram.controller"
})
public class SwaggerConfig {

    /** swagger */
    @Bean
    public Docket ShopApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("Outstagram")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.woo.outstagram.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.ShopApiInfo());

    }

    private ApiInfo ShopApiInfo() {
        return new ApiInfoBuilder()
                .title("Outstagram API")
                .description("Outstagram API")
                .version("1.0")
                .build();
    }
}
