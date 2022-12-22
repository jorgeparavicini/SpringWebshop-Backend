package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.dto.OrderDTOTest
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.services.OrderService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(controllers = [OrderController::class], excludeAutoConfiguration = [EmbeddedMongoAutoConfiguration::class])
class OrderControllerTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    lateinit var orderService: OrderService

    @Test
    fun givenUnauthorized_whenGetAllRequest_thenReturnsStatus401() {
        mockMvc.perform(get("/api/orders"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithMockUser
    fun givenAuthenticatedUser_whenGetAllRequest_thenReturnsOrdersJsonWithStatus200() {
        every { orderService.getAll() } returns OrderDTOTest.orderList

        mockMvc.perform(get("/api/orders"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$[0].comments").value(OrderDTOTest.order1.comments))
    }

    @Test
    fun givenUnauthorized_whenFindRequest_thenReturnsStatus401() {
        mockMvc.perform(get("/api/orders/1"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithMockUser
    fun givenAuthenticatedUser_whenFindRequest_thenReturnsOrderJsonWithStatus200() {
        every { orderService.find(1) } returns OrderDTOTest.order1

        mockMvc.perform(get("/api/orders/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.comments").value(OrderDTOTest.order1.comments))
    }

    @Test
    @WithMockUser
    fun givenAuthenticatedUserAndMissingOrder_whenFindRequest_thenReturnsStatus404() {
        every { orderService.find(1) } throws NotFoundException()

        mockMvc.perform(get("/api/orders/1"))
            .andExpect(status().isNotFound)
    }
}