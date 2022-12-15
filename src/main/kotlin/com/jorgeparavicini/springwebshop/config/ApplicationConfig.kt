package com.jorgeparavicini.springwebshop.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ApplicationConfig(
    @Value("\${cors.allowed-origins}")
    private val corsOrigin: String
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(corsOrigin)
            .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE)
            .allowedMethods(HttpMethod.GET.name, HttpMethod.POST.name, HttpMethod.PUT.name, HttpMethod.DELETE.name)
            .allowCredentials(true)
            .maxAge(86400)
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/upload/**")
            .addResourceLocations("file:///" + System.getProperty("user.dir") + "/upload/");
    }
}