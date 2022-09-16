package com.jorgeparavicini.springwebshop.models

import com.jorgeparavicini.springwebshop.database.entities.Product

data class ProductDTO(var id: Long, var name: String, var description: String, var price: Float, var categoryId: Long)