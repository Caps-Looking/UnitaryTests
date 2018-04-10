package com.example.demo.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDoc() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("Common")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controllers"))
                .paths(regex("/v1.*")).build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("House Helper")
                .version("1.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/license/LICENSE-2.0")
                .build();
    }

}
