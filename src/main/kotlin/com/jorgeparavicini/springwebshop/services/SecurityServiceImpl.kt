package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.exceptions.UnauthorizedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SecurityServiceImpl : SecurityService {
    override val userId: String
        get() = SecurityContextHolder.getContext()?.authentication?.name ?: throw UnauthorizedException()
}