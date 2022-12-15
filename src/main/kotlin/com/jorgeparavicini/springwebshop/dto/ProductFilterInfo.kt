package com.jorgeparavicini.springwebshop.dto

data class ProductFilterInfo(
    val categories: List<ProductCategoryDTO>,
    val vendors: List<VendorDTO>,
    val maxShippingCost: Float,
    val minPrice: Float,
    val maxPrice: Float,
)
