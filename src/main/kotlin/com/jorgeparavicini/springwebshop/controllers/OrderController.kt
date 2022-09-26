package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.dto.OrderDTO
import com.jorgeparavicini.springwebshop.services.OrderService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/orders"], produces = [MediaType.APPLICATION_JSON_VALUE])
class OrderController(private val service: OrderService) {

    @GetMapping
    fun getAll(): List<OrderDTO> {
        return service.getAll().toList()
    }

    @GetMapping("{id}")
    fun find(@PathVariable id: Long): OrderDTO {
        return service.find(id)
    }
}