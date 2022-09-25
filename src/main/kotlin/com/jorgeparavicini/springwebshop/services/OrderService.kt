package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.models.CreateOrderDTO
import com.jorgeparavicini.springwebshop.models.OrderDTO

interface OrderService {
    fun getAll(): List<OrderDTO>

    fun find(id: Long): OrderDTO

    fun create(dto: CreateOrderDTO): OrderDTO
}