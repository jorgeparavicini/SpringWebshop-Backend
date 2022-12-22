package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.config.security.SecurityConfig
import com.jorgeparavicini.springwebshop.dto.ProductCategoryDTOTest
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.services.ProductCategoryService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.test.context.support.TestExecutionEvent
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.context.support.WithSecurityContext
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [ProductCategoryController::class])
class ProductCategoryControllerTest(
    @Autowired val mockMvc: MockMvc
) {
    @MockkBean
    lateinit var categoryService: ProductCategoryService

    @Test
    @WithMockUser
    fun whenGetAllRequest_thenReturnsAllCategoriesWithStatus200() {
        every { categoryService.getAll() } returns ProductCategoryDTOTest.productCategoryList

        mockMvc.perform(get("/api/product-categories")).andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$[0].name").value(ProductCategoryDTOTest.productCategory1.name))
    }

    @Test
    @WithMockUser
    fun givenExistingCategory_whenFindRequest_thenReturnsCategoryWithStatus200() {
        every { categoryService.find(1) } returns ProductCategoryDTOTest.productCategory1

        mockMvc.perform(get("/api/product-categories/1")).andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.name").value(ProductCategoryDTOTest.productCategory1.name))
    }

    @Test
    @WithMockUser
    fun givenMissingCategory_whenFindRequest_thenReturnsStatus404() {
        every { categoryService.find(1) } throws NotFoundException()

        mockMvc.perform(get("/api/product-categories/1")).andExpect(status().isNotFound)
    }

    @Test
    fun givenUnauthorizedUser_whenCreateRequest_thenReturnsStatus403() {
        mockMvc.perform(post("/api/product-categories")).andExpect(status().isForbidden)
    }

    @Test
    fun givenUnauthorizedUser_whenUpdateRequest_thenReturnsStatus403() {
        mockMvc.perform(put("/api/product-categories")).andExpect(status().isForbidden)
    }

    @Test
    fun givenUnauthorizedUser_whenDeleteRequest_thenReturnsStatus403() {
        mockMvc.perform(delete("/api/product-categories")).andExpect(status().isForbidden)
    }
}