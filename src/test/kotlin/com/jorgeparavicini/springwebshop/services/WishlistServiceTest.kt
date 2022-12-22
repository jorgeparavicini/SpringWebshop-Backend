package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.repositories.ProductRepository
import com.jorgeparavicini.springwebshop.database.repositories.WishlistItemRepository
import com.jorgeparavicini.springwebshop.dto.WishlistItemDTOTest
import com.jorgeparavicini.springwebshop.entities.ProductTest
import com.jorgeparavicini.springwebshop.entities.WishlistItemTest
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
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

class WishlistServiceTest {
    // Repos
    private val repo: WishlistItemRepository = mockk()
    private val productRepo: ProductRepository = mockk()
    private val securityService: SecurityService = mockk()

    // SUT
    private val service = WishlistServiceImpl(repo, productRepo, securityService)


    @Test
    fun whenGetAll_thenReturnAll() {
        // Given
        every { repo.findByUserId("user1") } returns WishlistItemTest.wishlistList
        every { securityService.userId } returns "user1"

        // When
        val result = service.getAll().toList()

        // Then
        verify(exactly = 1) { repo.findByUserId("user1") }
        verify(exactly = 1) { securityService.userId }
        assertTrue(WishlistItemDTOTest.wishlistList.containsAll(result))
    }

    @Test
    fun givenExistingWishlistItem_whenFind_thenReturnWishlistItem() {
        // Given
        every { repo.findByIdAndUserId(1, "user1") } returns Optional.of(WishlistItemTest.wishlistItem1)
        every { securityService.userId } returns "user1"

        // When
        val result = service.find(1)

        // Then
        verify(exactly = 1) { repo.findByIdAndUserId(1, "user1") }
        assertEquals(WishlistItemDTOTest.wishlistItem1, result)
    }

    @Test
    fun givenNoWishlistItem_whenFind_thenThrowNotFound() {
        // Given
        every { repo.findByIdAndUserId(1, "user1") } returns Optional.empty()
        every { securityService.userId } returns "user1"

        // When
        val result = assertThrows<NotFoundException> { service.find(1) }

        // Then
        verify(exactly = 1) { repo.findByIdAndUserId(1, "user1") }
        assertEquals("Could not find wishlist item with id 1 for the current user", result.message)
    }

    @Test
    fun givenWishlistItem_whenCreate_thenCreateNewWishlistItem() {
        // Given
        every { repo.save(any()) } returns WishlistItemTest.wishlistItem1
        every { productRepo.findById(1) } returns Optional.of(ProductTest.product1)
        every { securityService.userId } returns "user1"

        // When
        val result = service.create(WishlistItemDTOTest.wishlistItem1)

        // Then
        verify(exactly = 1) { repo.save(any()) }
        verify(exactly = 1) { productRepo.findById(1) }
        assertEquals(WishlistItemDTOTest.wishlistItem1, result)
    }

    @Test
    fun givenWishlistItem_whenUpdate_thenUpdateWishlistItem() {
        // Given
        every { repo.findByIdAndUserId(1, "user1") } returns Optional.of(WishlistItemTest.wishlistItem1)
        every { repo.save(any()) } returns WishlistItemTest.wishlistItem1
        every { securityService.userId } returns "user1"
        every { productRepo.findById(1) } returns Optional.of(ProductTest.product1)

        // When
        val result = service.update(1, WishlistItemDTOTest.wishlistItem1)

        // Then
        verify(exactly = 1) { repo.save(any()) }
        verify(exactly = 1) { productRepo.findById(1) }
        assertEquals(WishlistItemDTOTest.wishlistItem1, result)
    }

    @Test
    fun givenInvalidAddress_whenUpdate_thenThrowBadRequest() {
        // Given
        every { repo.findByIdAndUserId(2, "user1") } returns Optional.empty()
        every { securityService.userId } returns "user1"

        // When
        val result =
            assertThrows<BadRequestException> { service.update(2, WishlistItemDTOTest.wishlistItem1) }
        // Then
        assertEquals("The passed id (2) does not match the id of the entity: 1", result.message)
    }

    @Test
    fun whenDelete_thenDeleteWishlistItem() {
        // Given
        every { repo.softDelete(1) } returns Unit
        every { repo.findByIdOrNull(1) } returns WishlistItemTest.wishlistItem1
        every { securityService.userId } returns "user1"

        // When
        service.delete(1)

        // Then
        verify(exactly = 1) { repo.softDelete(1) }
        verify { securityService.userId }
    }

    @Test
    fun givenUnauthorized_whenDelete_thenThrowUnauthorizedException() {
        // Given
        every { repo.findByIdOrNull(1) } returns WishlistItemTest.wishlistItem1
        every { securityService.userId } returns "user2"

        // When
        val result = assertThrows<UnauthorizedException> { service.delete(1) }

        // Then
        assertEquals("Can not delete item from another users wishlist", result.message)
    }
}