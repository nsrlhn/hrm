package com.example.hrm.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    GroupedOpenApi dayOff() {
        return GroupedOpenApi.builder().group("day-off").pathsToMatch("/**/day-off/**").build();
    }

    @Bean
    GroupedOpenApi test() {
        return GroupedOpenApi.builder().group("test").pathsToMatch("/**/test/**").build();
    }
}
