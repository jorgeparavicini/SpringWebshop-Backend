package com.jorgeparavicini.springwebshop.entities

import com.jorgeparavicini.springwebshop.database.entities.RecommendedProduct

class RecommendedProductTest {
    companion object {
        val recommendedProduct1 = RecommendedProduct(ProductTest.product1, 1)
        val recommendedProductList = listOf(recommendedProduct1)
    }
}