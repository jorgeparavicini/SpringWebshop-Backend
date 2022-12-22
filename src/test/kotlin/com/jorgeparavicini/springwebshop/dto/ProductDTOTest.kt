package com.jorgeparavicini.springwebshop.dto

class ProductDTOTest {
    companion object {
        val product1 = ProductDTO(
            1,
            "name1",
            "description1",
            10.0.toFloat(),
            null,
            "thumbnail1",
            0.0.toFloat(),
            1,
            1,
            0.0.toFloat(),
            0
        )

        val productList = listOf(product1)
    }
}