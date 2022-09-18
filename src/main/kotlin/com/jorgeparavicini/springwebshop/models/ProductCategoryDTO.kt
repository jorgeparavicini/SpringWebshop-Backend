package com.jorgeparavicini.springwebshop.models

data class ProductCategoryDTO(override var id: Long, var name: String, var description: String?): DTO()
