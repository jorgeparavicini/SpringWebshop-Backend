package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.dto.ProductDTO

interface RecommendedProductsService {
    fun getAll(): Iterable<ProductDTO>

    fun regenerate()
}