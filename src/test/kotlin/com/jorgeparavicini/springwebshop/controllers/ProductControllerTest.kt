package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.dto.ProductCategoryDTOTest
import com.jorgeparavicini.springwebshop.dto.ProductDTOTest
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.services.ProductService
import com.jorgeparavicini.springwebshop.services.RecommendedProductsService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [ProductController::class])
class ProductControllerTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    lateinit var service: ProductService

    @MockkBean
    lateinit var recommendedProductService: RecommendedProductsService

    @Test
    @WithMockUser
    fun givenExistingProduct_whenFindRequest_thenReturnsProductWithStatus200() {
        every { service.find(1) } returns ProductDTOTest.product1

        mockMvc.perform(get("/api/products/1"))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(ProductDTOTest.product1.name))
    }

    @Test
    @WithMockUser
    fun givenMissingProduct_whenFindRequest_thenReturnsStatus404() {
        every { service.find(1) } throws NotFoundException()

        mockMvc.perform(get("/api/products/1")).andExpect(status().isNotFound)
    }

    @Test
    fun givenUnauthorizedUser_whenCreateRequest_thenReturnsStatus403() {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products"))
            .andExpect(status().isForbidden)
    }

    @Test
    fun givenUnauthorizedUser_whenUpdateRequest_thenReturnsStatus403() {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/products"))
            .andExpect(status().isForbidden)
    }

    @Test
    fun givenUnauthorizedUser_whenDeleteRequest_thenReturnsStatus403() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products"))
            .andExpect(status().isForbidden)
    }
}