package com.jorgeparavicini.springwebshop.dto

import java.time.LocalDateTime

data class OrderDTO(
    override var id: Long,
    val comments: String,
    val address_id: Long,
    val orderItems: Set<OrderItemDTO>,
    val date: LocalDateTime,
    val totalPrice: Double,
) : DTO()
