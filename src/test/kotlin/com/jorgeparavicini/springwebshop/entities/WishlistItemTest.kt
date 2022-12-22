package com.jorgeparavicini.springwebshop.entities

import com.jorgeparavicini.springwebshop.database.entities.WishlistItem

class WishlistItemTest {
    companion object {
        val wishlistItem1 = WishlistItem(ProductTest.product1, "user1", 1)

        val wishlistList = listOf(wishlistItem1)
    }
}