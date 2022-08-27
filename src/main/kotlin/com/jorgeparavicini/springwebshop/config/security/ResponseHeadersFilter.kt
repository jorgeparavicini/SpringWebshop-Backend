package com.jorgeparavicini.springwebshop.config.security

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletResponse

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class ResponseHeadersFilter: Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {
        val httpResponse = response as HttpServletResponse

        httpResponse.setIntHeader("X-XSS-Protection", 0)
        httpResponse.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains")
        httpResponse.setHeader("X-Frame-Options", "deny")
        httpResponse.setHeader("X-Content-Type-Options", "nosniff")
        httpResponse.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0, must-revalidate")
        httpResponse.setHeader(HttpHeaders.PRAGMA, "no-cache")
        httpResponse.setIntHeader(HttpHeaders.EXPIRES, 0)

        chain.doFilter(request, response)
    }
}