package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.dto.OrderDTOTest
import com.jorgeparavicini.springwebshop.dto.ShoppingCartItemDTOTest
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.services.OrderService
import com.jorgeparavicini.springwebshop.services.ShoppingCartService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(controllers = [ShoppingCartController::class])
class ShoppingCartControllerTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    lateinit var service: ShoppingCartService

    @MockkBean
    lateinit var orderService: OrderService

    @Test
    fun givenUnauthorized_whenGetAllRequest_thenReturnsStatus401() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/shopping-cart"))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    @WithMockUser
    fun givenAuthenticatedUser_whenGetAllRequest_thenReturnsShoppingCartJsonWithStatus200() {
        every { service.getAll() } returns ShoppingCartItemDTOTest.shoppingCartItemList

        mockMvc.perform(MockMvcRequestBuilders.get("/api/shopping-cart"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(ShoppingCartItemDTOTest.shoppingCartItem1.id)
            )
    }

    @Test
    fun givenUnauthorized_whenFindRequest_thenReturnsStatus401() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/shopping-cart/1"))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    @WithMockUser
    fun givenAuthenticatedUser_whenFindRequest_thenReturnsOrderJsonWithStatus200() {
        every { service.find(1) } returns ShoppingCartItemDTOTest.shoppingCartItem1

        mockMvc.perform(MockMvcRequestBuilders.get("/api/shopping-cart/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(ShoppingCartItemDTOTest.shoppingCartItem1.id))
    }

    @Test
    @WithMockUser
    fun givenAuthenticatedUserAndMissingOrder_whenFindRequest_thenReturnsStatus404() {
        every { service.find(1) } throws NotFoundException()

        mockMvc.perform(MockMvcRequestBuilders.get("/api/shopping-cart/1"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun givenUnauthorizedUser_whenCreateRequest_thenReturnsStatus403() {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/shopping-cart"))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun givenUnauthorizedUser_whenUpdateRequest_thenReturnsStatus403() {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/shopping-cart"))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun givenUnauthorizedUser_whenDeleteRequest_thenReturnsStatus403() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/shopping-cart"))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun givenUnauthorizedUser_whenOrderRequest_thenReturnsStatus403() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/shopping-cart/order"))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }
}