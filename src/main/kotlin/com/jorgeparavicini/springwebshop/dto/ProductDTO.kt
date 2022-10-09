package com.jorgeparavicini.springwebshop.dto

data class ProductDTO(
    override var id: Long,
    var name: String,
    var description: String,
    var thumbnailUri: String,
    var price: Float,
    var categoryId: Long,
    var vendorId: Long
) : DTO()