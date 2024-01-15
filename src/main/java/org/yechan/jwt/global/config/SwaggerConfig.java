package org.yechan.jwt.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {
    private static final String JWT_SCHEMA = "JWT";
    @Bean
    public OpenAPI openAPI() {
        Info info = initInfo();
        
        String jwtSchemeName = "jwtAuth";

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        Components components = new Components()
                .addSecuritySchemes(
                        jwtSchemeName,
                        new SecurityScheme()
                                .name(jwtSchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .in(SecurityScheme.In.HEADER)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );
        
        return new OpenAPI()
                .info(info)
                .components(components)
                .addSecurityItem(securityRequirement);
    }
    
    @Bean
    public GroupedOpenApi authentication() {
        return GroupedOpenApi.builder()
                .group("인증 관련")
                .pathsToMatch("/auth/**")
                .build();
    }
    
    @Bean
    public GroupedOpenApi account() {
        return GroupedOpenApi.builder()
                .group("회원 관련")
                .pathsToMatch("/account/**")
                .build();
    }
    
    private static SecurityScheme initSecuritySchema() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);
    }
    
    private static Info initInfo() {
        return new Info()
                .title("auth service API Document")
                .version("v0.0.1")
                .description("인증 인가 프로젝트의 API 명세서입니다.")
                .contact(new Contact()
                        .name("YeaChan")
                        .email("qkenrdl05@gmail.com"));
    }
}