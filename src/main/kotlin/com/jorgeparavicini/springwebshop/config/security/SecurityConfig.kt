package com.jorgeparavicini.springwebshop.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2ErrorCodes
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    @Value("\${auth0.audience}")
    private val audience: String,
    private val authenticationErrorHandler: AuthenticationErrorHandler,
    private val resourceServerProps: OAuth2ResourceServerProperties,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET).permitAll()
            .anyRequest().authenticated()
            .and().cors()
            .and().oauth2ResourceServer()
            .authenticationEntryPoint(authenticationErrorHandler)
            .jwt().decoder(makeJwtDecoder())
            .jwtAuthenticationConverter(makePermissionConverter())

        return http.build()
    }

    @Bean
    fun makeJwtDecoder(): JwtDecoder {
        val issuer = resourceServerProps.jwt.issuerUri
        val decoder = JwtDecoders.fromIssuerLocation<NimbusJwtDecoder>(issuer)
        val withIssuer = JwtValidators.createDefaultWithIssuer(issuer)
        val tokenValidator = DelegatingOAuth2TokenValidator(withIssuer, this::withAudience)

        decoder.setJwtValidator(tokenValidator)
        return decoder
    }

    private fun withAudience(token: Jwt): OAuth2TokenValidatorResult {
        val audienceError = OAuth2Error(
            OAuth2ErrorCodes.INVALID_TOKEN,
            "The token was not issued for the given audience",
            "https://datatracker.ietf.org/doc/html/rfc6750#section-3.1"
        )

        return if (token.audience.contains(audience))
            OAuth2TokenValidatorResult.success()
        else OAuth2TokenValidatorResult.failure(audienceError)
    }

    private fun makePermissionConverter(): JwtAuthenticationConverter {
        val jwtAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        jwtAuthoritiesConverter.setAuthoritiesClaimName("permissions")
        jwtAuthoritiesConverter.setAuthorityPrefix("")

        val jwtAuthConverter = JwtAuthenticationConverter()
        jwtAuthConverter.setJwtGrantedAuthoritiesConverter(jwtAuthoritiesConverter)

        return jwtAuthConverter
    }
}