package com.jorgeparavicini.springwebshop.entities

import com.jorgeparavicini.springwebshop.database.entities.ShoppingCartItem

class ShoppingCartItemTest {
    companion object {
        val shoppingCartItem1 = ShoppingCartItem(ProductTest.product1, 4, "user1", 1)

        val shoppingCartItemList = listOf(shoppingCartItem1)
    }
}