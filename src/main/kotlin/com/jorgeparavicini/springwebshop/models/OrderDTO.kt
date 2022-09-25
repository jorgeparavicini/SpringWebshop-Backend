package com.jorgeparavicini.springwebshop.models

data class OrderDTO(
    override var id: Long,
    var comments: String,
    var street: String,
    var city: String,
    var postalCode: String,
    var country: String,
    var orderItems: Set<OrderItemDTO>
) : DTO()
