package com.memo.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(title = "memo api",
				description = "memo 서비스 API 문서",
				version = "v1")
		)
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi chatOpenApi() {
		String[] paths = {"/**"};
		
		 return GroupedOpenApi.builder()
	                .group("MEMO API")
	                .pathsToMatch(paths)
	                .build();
	}
}
