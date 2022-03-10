package de.szut.shift_backend.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;

@Configuration
public class OpenAPIConfiguration {

    private ServletContext context;

    public OpenAPIConfiguration(ServletContext context) {
        this.context = context;
    }


    @Bean
    public OpenAPI springShopOpenAPI(
            //  @Value("${info.app.version}") String appVersion,
    ) {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .addServersItem(new Server().url(this.context.getContextPath()))
                .info(new Info()
                        .title("Shiftplan Backend")
                        .description("\n## Auth\n" +
                                "\n## Authentication\n" + "\nThis Hello service uses JWTs to authenticate requests. You will receive a bearer token by making a POST-Request in IntelliJ on:\n\n" +
                                "\n" +
                                "```\nPOST http://keycloak.szut.dev/auth/realms/szut/protocol/openid-connect/token\nContent-Type: application/x-www-form-urlencoded\ngrant_type=password&client_id=employee-management-service&username=user&password=test\n```\n" +
                                "\n" +
                                "\nor by CURL\n" +
                                "```\ncurl -X POST 'http://keycloak.szut.dev/auth/realms/szut/protocol/openid-connect/token'\n--header 'Content-Type: application/x-www-form-urlencoded'\n--data-urlencode 'grant_type=password'\n--data-urlencode 'client_id=employee-management-service'\n--data-urlencode 'username=user'\n--data-urlencode 'password=test'\n```\n" +
                                "\nTo get a bearer-token in Postman, you have to follow the instructions in \n [Postman-Documentation](https://documenter.getpostman.com/view/7294517/SzmfZHnd).")

                        .version("0.1"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }


}