package com.jorgeparavicini.springwebshop.dto

data class ProductCategoryDTO(
    override var id: Long,
    var name: String,
    var description: String?,
    var parentCategory: Long?
) : DTO()
