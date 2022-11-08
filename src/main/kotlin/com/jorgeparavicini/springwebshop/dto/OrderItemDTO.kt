package com.jorgeparavicini.springwebshop.dto

data class OrderItemDTO(
    override var id: Long,
    var productId: Long,
    var quantity: Int,
) : DTO()
