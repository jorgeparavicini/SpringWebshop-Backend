package com.jorgeparavicini.springwebshop.dto

data class ProductFilterInfo(
    val categories: List<Long>,
    val vendors: List<Long>,
    val maxShippingCost: Float,
    val minPrice: Float,
    val maxPrice: Float,
)
