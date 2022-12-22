package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.OrderItem
import com.jorgeparavicini.springwebshop.database.repositories.AddressRepository
import com.jorgeparavicini.springwebshop.database.repositories.OrderItemRepository
import com.jorgeparavicini.springwebshop.database.repositories.OrderRepository
import com.jorgeparavicini.springwebshop.database.repositories.ShoppingCartItemRepository
import com.jorgeparavicini.springwebshop.dto.CreateOrderDTOTest
import com.jorgeparavicini.springwebshop.dto.OrderDTOTest
import com.jorgeparavicini.springwebshop.entities.AddressTest
import com.jorgeparavicini.springwebshop.entities.OrderTest
import com.jorgeparavicini.springwebshop.entities.ShoppingCartItemTest
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OrderServiceTest {
    // Repos
    private val orderRepository: OrderRepository = mockk()
    private val orderItemRepository: OrderItemRepository = mockk()
    private val shoppingCartRepository: ShoppingCartItemRepository = mockk()
    private val addressRepository: AddressRepository = mockk()
    private val securityService: SecurityService = mockk()

    // SUT
    private val orderService =
        OrderServiceImpl(
            orderRepository,
            orderItemRepository,
            shoppingCartRepository,
            addressRepository,
            securityService
        )

    @Test
    fun whenGetAll_thenReturnAll() {
        // Given
        every { orderRepository.getAllByUserIdOrderByDate("user1") } returns OrderTest.orderList
        every { securityService.userId } returns "user1"

        // When
        val result = orderService.getAll()

        // Then
        verify(exactly = 1) { orderRepository.getAllByUserIdOrderByDate("user1") }
        verify(exactly = 1) { securityService.userId }
        assertTrue(OrderDTOTest.orderList.containsAll(result))
    }

    @Test
    fun givenExistingOrder_whenFind_thenReturnOrder() {
        // Given
        every { orderRepository.findByIdAndUserId(1, "user1") } returns OrderTest.order1
        every { securityService.userId } returns "user1"

        // When
        val result = orderService.find(1)

        // Then
        verify(exactly = 1) { orderRepository.findByIdAndUserId(1, "user1") }
        verify(exactly = 1) { securityService.userId }
        assertEquals(OrderDTOTest.order1, result)
    }

    @Test
    fun givenNoOrder_whenFind_thenThrowNotFound() {
        // Given
        every { orderRepository.findByIdAndUserId(1, "user1") } returns null
        every { securityService.userId } returns "user1"

        // When
        val exception = assertThrows<NotFoundException> { orderService.find(1) }

        // Then
        verify(exactly = 1) { orderRepository.findByIdAndUserId(1, "user1") }
        verify(exactly = 1) { securityService.userId }
        assertEquals("Could not find order", exception.message)
    }

    @Test
    fun givenCreateOrderDTO_whenCreate_thenReturnNewOrderDTO() {
        // Given
        every { shoppingCartRepository.findByUserId("user1") } returns ShoppingCartItemTest.shoppingCartItemList
        every { addressRepository.findByIdOrNull(1) } returns AddressTest.address1
        every { orderItemRepository.saveAll<OrderItem>(any()) } returns emptyList()
        every { shoppingCartRepository.deleteAllByUserId("user1") } returns Unit
        every { securityService.userId } returns "user1"
        every { orderRepository.save(any()) } returns OrderTest.order1

        // When
        val result = orderService.create(CreateOrderDTOTest.createOrder1)

        // Then
        verify(exactly = 1) { shoppingCartRepository.findByUserId("user1") }
        verify(exactly = 1) { addressRepository.findByIdOrNull(1) }
        verify(exactly = 1) { orderItemRepository.saveAll<OrderItem>(any()) }
        verify(exactly = 1) { shoppingCartRepository.deleteAllByUserId("user1") }
        verify { securityService.userId }
        assertEquals(OrderDTOTest.order1, result)
    }

    @Test
    fun givenInvalidOrder_whenCreate_throwBadRequest() {
        // Given
        every { shoppingCartRepository.findByUserId("user1") } returns emptyList()
        every { securityService.userId } returns "user1"
        every { addressRepository.findByIdOrNull(1) } returns AddressTest.address1

        // When
        val result = assertThrows<BadRequestException> { orderService.create(CreateOrderDTOTest.createOrder1) }

        // Assert
        assertEquals("Can not order an empty shopping cart.", result.message)
    }
}