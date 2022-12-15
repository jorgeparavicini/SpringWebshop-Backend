package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.dto.WishlistItemDTO
import com.jorgeparavicini.springwebshop.services.WishlistService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["api/wishlist"], produces = [MediaType.APPLICATION_JSON_VALUE])
class WishlistController(private val service: WishlistService) {
    @GetMapping
    fun getAll(): List<WishlistItemDTO> {
        return service.getAll().toList()
    }

    @GetMapping("{id}")
    fun find(@PathVariable id: Long): WishlistItemDTO {
        return service.find(id)
    }

    @PostMapping()
    fun create(@Valid @RequestBody newWishlistItem: WishlistItemDTO): WishlistItemDTO {
        return service.create(newWishlistItem)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) {
        service.delete(id)
    }
}