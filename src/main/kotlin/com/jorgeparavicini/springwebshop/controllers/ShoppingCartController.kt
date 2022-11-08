package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.dto.CreateOrderDTO
import com.jorgeparavicini.springwebshop.dto.OrderDTO
import com.jorgeparavicini.springwebshop.dto.ShoppingCartItemDTO
import com.jorgeparavicini.springwebshop.services.OrderService
import com.jorgeparavicini.springwebshop.services.ShoppingCartService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["api/shopping-cart"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ShoppingCartController(private val service: ShoppingCartService, private val orderService: OrderService) {
    @GetMapping
    fun getAll(): List<ShoppingCartItemDTO> {
        return service.getAll().toList()
    }

    @GetMapping("{id}")
    fun find(@PathVariable id: Long): ShoppingCartItemDTO {
        return service.find(id)
    }

    @PostMapping
    fun create(@Valid @RequestBody newShoppingCartItem: ShoppingCartItemDTO): ShoppingCartItemDTO {
        return service.create(newShoppingCartItem)
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody newShoppingCartItem: ShoppingCartItemDTO): ShoppingCartItemDTO {
        return service.update(id, newShoppingCartItem)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) {
        service.delete(id)
    }

    @PostMapping("order")
    fun create(@Valid @RequestBody createOrderDTO: CreateOrderDTO): OrderDTO {
        return orderService.create(createOrderDTO)
    }
}