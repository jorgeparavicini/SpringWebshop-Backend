package com.jorgeparavicini.springwebshop.dto

data class WishlistItemDTO(
    override var id: Long,
    val productId: Long
) : DTO()
