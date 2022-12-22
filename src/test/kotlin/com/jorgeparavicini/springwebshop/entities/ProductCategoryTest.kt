package com.jorgeparavicini.springwebshop.entities

import com.jorgeparavicini.springwebshop.database.entities.ProductCategory

class ProductCategoryTest {
    companion object {
        val productCategory1 = ProductCategory("name1", "description1", "icon1", null, emptyList(), emptyList(), 1)

        val productCategoryList = listOf(productCategory1)
    }
}