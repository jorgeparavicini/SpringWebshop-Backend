package com.jorgeparavicini.springwebshop.models

data class OrderItemDTO(
    override var id: Long,
    var productId: Long,
    var quantity: Int,
) : DTO()
