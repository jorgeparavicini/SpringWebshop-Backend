package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.models.DTO
import com.jorgeparavicini.springwebshop.models.ShoppingCartItemDTO
import com.jorgeparavicini.springwebshop.services.ShoppingCartService
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping(path = ["api/shopping-cart"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ShoppingCartController(private val service: ShoppingCartService) {
    @GetMapping
    fun getAll(): List<ShoppingCartItemDTO> {
        return service.getAll().toList()
    }

    @GetMapping("{id}")
    fun find(@PathVariable id: Long): ShoppingCartItemDTO {
        return service.find(id)
    }

    @PostMapping
    fun create(@RequestBody newShoppingCartItem: ShoppingCartItemDTO): ShoppingCartItemDTO {
        return service.create(newShoppingCartItem)
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody newShoppingCartItem: ShoppingCartItemDTO): ShoppingCartItemDTO {
        return service.update(id, newShoppingCartItem)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) {
        service.delete(id)
    }
}