package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.dto.CreateOrderDTO
import com.jorgeparavicini.springwebshop.dto.OrderDTO

interface OrderService {
    fun getAll(): List<OrderDTO>

    fun find(id: Long): OrderDTO

    fun create(dto: CreateOrderDTO): OrderDTO
}