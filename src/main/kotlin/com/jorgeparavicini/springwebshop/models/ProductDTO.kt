package com.jorgeparavicini.springwebshop.models

data class ProductDTO(
    override var id: Long,
    var name: String,
    var description: String,
    var price: Float,
    var categoryId: Long
) : DTO()