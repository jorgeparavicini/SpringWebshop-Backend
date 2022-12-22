package com.jorgeparavicini.springwebshop.entities

import com.jorgeparavicini.springwebshop.database.entities.Product

class ProductTest {
    companion object {
        val product1 = Product(
            "name1",
            "description1",
            10.0.toFloat(),
            0.0.toFloat(),
            null,
            "thumbnail1",
            ProductCategoryTest.productCategory1,
            VendorTest.vendor1,
            1
        )

        val productList = listOf(product1)
    }
}