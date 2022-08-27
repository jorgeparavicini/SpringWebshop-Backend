package com.jorgeparavicini.springwebshop.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springdoc.api.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationErrorHandler(private val mapper: ObjectMapper) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val message = "Unauthorized ${authException.message}"
        val errorMessage = ErrorMessage(message)
        val json = mapper.writeValueAsString(errorMessage)

        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(json)
        response.flushBuffer()
    }
}