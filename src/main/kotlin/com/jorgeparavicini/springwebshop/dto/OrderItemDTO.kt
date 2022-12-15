package com.jorgeparavicini.springwebshop.dto

data class OrderItemDTO(
    override var id: Long,
    val productId: Long,
    val quantity: Int,
) : DTO()
