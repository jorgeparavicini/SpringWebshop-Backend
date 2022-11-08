package com.jorgeparavicini.springwebshop.dto

import org.springframework.data.domain.Page

data class ProductListFilterInfo(
    val products: Page<ProductDTO>,
    val filterInfo: ProductFilterInfo
)
