package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.dto.WishlistItemDTOTest
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.services.WishlistService
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

@WebMvcTest(controllers = [WishlistController::class])
class WishlistControllerTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    lateinit var service: WishlistService

    @Test
    @WithMockUser
    fun whenGetAllRequest_thenReturnsAllCategoriesWithStatus200() {
        every { service.getAll() } returns WishlistItemDTOTest.wishlistList

        mockMvc.perform(MockMvcRequestBuilders.get("/api/wishlist"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$[0].productId").value(WishlistItemDTOTest.wishlistItem1.productId)
            )
    }

    @Test
    @WithMockUser
    fun givenExistingCategory_whenFindRequest_thenReturnsUserAddressWithStatus200() {
        every { service.find(1) } returns WishlistItemDTOTest.wishlistItem1

        mockMvc.perform(MockMvcRequestBuilders.get("/api/wishlist/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(WishlistItemDTOTest.wishlistItem1.productId))
    }

    @Test
    @WithMockUser
    fun givenMissingCategory_whenFindRequest_thenReturnsStatus404() {
        every { service.find(1) } throws NotFoundException()

        mockMvc.perform(MockMvcRequestBuilders.get("/api/wishlist/1"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun givenUnauthorizedUser_whenCreateRequest_thenReturnsStatus403() {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/wishlist"))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun givenUnauthorizedUser_whenUpdateRequest_thenReturnsStatus403() {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/wishlist"))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun givenUnauthorizedUser_whenDeleteRequest_thenReturnsStatus403() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/wishlist"))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }
}