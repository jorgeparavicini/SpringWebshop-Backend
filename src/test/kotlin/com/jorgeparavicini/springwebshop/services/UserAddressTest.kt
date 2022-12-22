package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.repositories.AddressRepository
import com.jorgeparavicini.springwebshop.database.repositories.UserAddressRepository
import com.jorgeparavicini.springwebshop.dto.UserAddressDTOTest
import com.jorgeparavicini.springwebshop.entities.AddressTest
import com.jorgeparavicini.springwebshop.entities.UserAddressTest
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.exceptions.UnauthorizedException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserAddressTest {
    // Repos
    private val repo: UserAddressRepository = mockk()
    private val addressRepo: AddressRepository = mockk()
    private val securityService: SecurityService = mockk()

    // SUT
    private val service = UserAddressServiceImpl(repo, addressRepo, securityService)


    @Test
    fun whenGetAll_thenReturnAll() {
        // Given
        every { repo.getAllByUserId("user1") } returns UserAddressTest.userAddressList
        every { securityService.userId } returns "user1"

        // When
        val result = service.getAll().toList()

        // Then
        verify(exactly = 1) { repo.getAllByUserId("user1") }
        verify(exactly = 1) { securityService.userId }
        assertTrue(UserAddressDTOTest.userAddressList.containsAll(result))
    }

    @Test
    fun givenExistingUserAddress_whenFind_thenReturnUserAddress() {
        // Given
        every { repo.findByUserIdAndId("user1", 1) } returns Optional.of(UserAddressTest.userAddress1)
        every { securityService.userId } returns "user1"

        // When
        val result = service.find(1)

        // Then
        verify(exactly = 1) { repo.findByUserIdAndId("user1", 1) }
        assertEquals(UserAddressDTOTest.userAddress1, result)
    }

    @Test
    fun givenNoUserAddress_whenFind_thenThrowNotFound() {
        // Given
        every { repo.findByUserIdAndId("user1", 1) } returns Optional.empty()
        every { securityService.userId } returns "user1"

        // When
        val result = assertThrows<NotFoundException> { service.find(1) }

        // Then
        verify(exactly = 1) { repo.findByUserIdAndId("user1", 1) }
        assertEquals("User address not found", result.message)
    }

    @Test
    fun givenUserAddress_whenCreate_thenCreateNewUserAddress() {
        // Given
        every { repo.save(any()) } returns UserAddressTest.userAddress1
        every { addressRepo.save(any()) } returns AddressTest.address1
        every { securityService.userId } returns "user1"

        // When
        val result = service.create(UserAddressDTOTest.userAddress1)

        // Then
        verify(exactly = 1) { repo.save(any()) }
        assertEquals(UserAddressDTOTest.userAddress1, result)
    }

    @Test
    fun givenUserAddress_whenUpdate_thenUpdateUserAddress() {
        // Given
        every { repo.findByUserIdAndId("user1", 1) } returns Optional.of(UserAddressTest.userAddress1)
        every { repo.save(any()) } returns UserAddressTest.userAddress1
        every { securityService.userId } returns "user1"
        every { addressRepo.save(any()) } returns AddressTest.address1

        // When
        val result = service.update(1, UserAddressDTOTest.userAddress1)

        // Then
        verify(exactly = 1) { repo.save(any()) }
        assertEquals(UserAddressDTOTest.userAddress1, result)
    }

    @Test
    fun givenInvalidAddress_whenUpdate_thenThrowNotFound() {
        // Given
        every { repo.findByUserIdAndId("user1", 2) } returns Optional.empty()
        every { securityService.userId } returns "user1"

        // When
        val result =
            assertThrows<NotFoundException> { service.update(2, UserAddressDTOTest.userAddress1) }
        // Then
        assertEquals("User address not found", result.message)
    }

    @Test
    fun whenDelete_thenDeleteUserAddress() {
        // Given
        every { repo.softDelete(1, "user1") } returns Unit
        every { repo.findByIdOrNull(1) } returns UserAddressTest.userAddress1
        every { securityService.userId } returns "user1"

        // When
        service.delete(1)

        // Then
        verify(exactly = 1) { repo.softDelete(1, "user1") }
        verify { securityService.userId }
    }

    @Test
    fun givenUnauthorized_whenDelete_thenThrowUnauthorizedException() {
        // Given
        every { repo.findByIdOrNull(1) } returns UserAddressTest.userAddress1
        every { securityService.userId } returns "user2"

        // When
        val result = assertThrows<UnauthorizedException> { service.delete(1) }

        // Then
        assertEquals("Can not delete another users address", result.message)
    }
}
