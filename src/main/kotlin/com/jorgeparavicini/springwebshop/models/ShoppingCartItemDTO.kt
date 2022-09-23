package com.jorgeparavicini.springwebshop.models

data class ShoppingCartItemDTO(
    override var id: Long,
    var productId: Long,
    var quantity: Int
): DTO()
