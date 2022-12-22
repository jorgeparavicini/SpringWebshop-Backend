package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.exceptions.UnauthorizedException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SecurityServiceTest {
    private val service = SecurityServiceImpl()

    @Test
    fun givenNoAuthenticatedUser_whenUserId_thenThrowUnauthorized() {
        assertThrows<UnauthorizedException> { service.userId }
    }
}