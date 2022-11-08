package com.jorgeparavicini.springwebshop.dto

import javax.validation.constraints.Min

data class ShoppingCartItemDTO(
    override var id: Long,
    var productId: Long,

    @field:Min(1)
    var quantity: Int
): DTO()
