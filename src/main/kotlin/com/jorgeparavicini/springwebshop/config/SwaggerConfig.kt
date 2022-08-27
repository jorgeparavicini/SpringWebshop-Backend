package com.jorgeparavicini.springwebshop.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.OAuthFlow
import io.swagger.v3.oas.models.security.OAuthFlows
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig(
    @Value("\${info.api-name}")
    private val apiName: String,
    @Value("\${info.version}")
    private val apiVersion: String,
    @Value("\${auth0.domain}")
    private val domain: String,
    @Value("\${auth0.audience}")
    private val audience: String
) {

    @Bean
    fun apiInfo(): OpenAPI {
        val securitySchemeName = "Bearer"
        val securityScheme = SecurityScheme()
            .name("Authorization")
            .`in`(SecurityScheme.In.HEADER)
            .type(SecurityScheme.Type.OAUTH2)
            .flows(
                OAuthFlows().implicit(
                    OAuthFlow().authorizationUrl("https://${domain}/authorize?audience=${audience}")
                )
            )

        return OpenAPI()
            .addSecurityItem(SecurityRequirement().addList(securitySchemeName))
            .components(
                Components().addSecuritySchemes(
                    securitySchemeName, securityScheme
                )
            )
            .info(
                Info()
                    .title(apiName)
                    .description("The backend service for a webshop made in spring.")
                    .version(apiVersion)
                    .license(License().name("MIT"))
                    .contact(Contact().name("Jorge Paravicini").email("j.m.paravicini@gmail.com"))
            )
    }
}