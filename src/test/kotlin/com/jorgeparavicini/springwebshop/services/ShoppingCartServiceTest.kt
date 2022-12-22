package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.repositories.ProductRepository
import com.jorgeparavicini.springwebshop.database.repositories.ShoppingCartItemRepository
import com.jorgeparavicini.springwebshop.dto.ShoppingCartItemDTOTest
import com.jorgeparavicini.springwebshop.entities.ProductTest
import com.jorgeparavicini.springwebshop.entities.ShoppingCartItemTest
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

class ShoppingCartServiceTest {
    // Repos
    private val repo: ShoppingCartItemRepository = mockk()
    private val productRepo: ProductRepository = mockk()
    private val securityService: SecurityService = mockk()

    // SUT
    private val service = ShoppingCartServiceImpl(repo, productRepo, securityService)

    @Test
    fun whenGetAll_thenReturnAll() {
        // Given
        every { repo.findByUserId("user1") } returns ShoppingCartItemTest.shoppingCartItemList
        every { securityService.userId } returns "user1"

        // When
        val result = service.getAll().toList()

        // Then
        verify(exactly = 1) { repo.findByUserId("user1") }
        verify(exactly = 1) { securityService.userId }
        assertTrue(ShoppingCartItemDTOTest.shoppingCartItemList.containsAll(result))
    }

    @Test
    fun givenExistingCartItem_whenFind_thenReturnCartItem() {
        // Given
        every { repo.findByIdAndUserId(1, "user1") } returns Optional.of(ShoppingCartItemTest.shoppingCartItem1)
        every { securityService.userId } returns "user1"

        // When
        val result = service.find(1)

        // Then
        verify(exactly = 1) { repo.findByIdAndUserId(1, "user1") }
        assertEquals(ShoppingCartItemDTOTest.shoppingCartItem1, result)
    }

    @Test
    fun givenNoCartItem_whenFind_thenThrowNotFound() {
        // Given
        every { repo.findByIdAndUserId(1, "user1") } returns Optional.empty()
        every { securityService.userId } returns "user1"

        // When
        val result = assertThrows<NotFoundException> { service.find(1) }

        // Then
        verify(exactly = 1) { repo.findByIdAndUserId(1, "user1") }
        assertEquals("Could not find shopping cart item with id 1 for the current user", result.message)
    }

    @Test
    fun givenCartItem_whenCreate_thenCreateNewCartItem() {
        // Given
        every { repo.save(any()) } returns ShoppingCartItemTest.shoppingCartItem1
        every { repo.findById(1) } returns Optional.of(ShoppingCartItemTest.shoppingCartItem1)
        every { productRepo.findById(1) } returns Optional.of(ProductTest.product1)
        every { securityService.userId } returns "user1"

        // When
        val result = service.create(ShoppingCartItemDTOTest.shoppingCartItem1)

        // Then
        verify(exactly = 1) { repo.save(any()) }
        assertEquals(ShoppingCartItemDTOTest.shoppingCartItem1, result)
    }

    @Test
    fun givenCartItem_whenUpdate_thenUpdateCartItem() {
        // Given
        every { repo.save(any()) } returns ShoppingCartItemTest.shoppingCartItem1
        every { repo.findById(1) } returns Optional.of(ShoppingCartItemTest.shoppingCartItem1)
        every { securityService.userId } returns "user1"
        every { productRepo.findById(1) } returns Optional.of(ProductTest.product1)

        // When
        val result = service.update(1, ShoppingCartItemDTOTest.shoppingCartItem1)

        // Then
        verify(exactly = 1) { repo.save(any()) }
        assertEquals(ShoppingCartItemDTOTest.shoppingCartItem1, result)
    }

    @Test
    fun givenInvalidCartItem_whenUpdate_thenThrowBadRequest() {
        // When
        val result = assertThrows<BadRequestException> { service.update(2, ShoppingCartItemDTOTest.shoppingCartItem1) }
        // Then
        assertEquals("The passed id (2) does not match the id of the entity: 1", result.message)
    }

    @Test
    fun whenDelete_thenDeleteCartItem() {
        // Given
        every { repo.softDelete(1) } returns Unit
        every { repo.findByIdOrNull(1) } returns ShoppingCartItemTest.shoppingCartItem1
        every { securityService.userId } returns "user1"

        // When
        service.delete(1)

        // Then
        verify(exactly = 1) { repo.softDelete(1) }
        verify(exactly = 1) { securityService.userId }
    }

    @Test
    fun givenUnauthorized_whenDelete_thenThrowUnauthorizedException() {
        // Given
        every { repo.findByIdOrNull(1) } returns ShoppingCartItemTest.shoppingCartItem1
        every { securityService.userId } returns "user2"

        // When
        val result = assertThrows<UnauthorizedException> { service.delete(1) }

        // Then
        assertEquals("Can not delete item from another users cart", result.message)
    }
}