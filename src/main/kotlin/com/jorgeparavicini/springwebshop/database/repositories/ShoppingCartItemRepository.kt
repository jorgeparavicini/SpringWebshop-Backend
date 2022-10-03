package com.jorgeparavicini.springwebshop.database.repositories

import com.jorgeparavicini.springwebshop.database.entities.ShoppingCartItem
import java.util.*

interface ShoppingCartItemRepository : BaseRepository<ShoppingCartItem> {
    fun findByUserId(userId: String): List<ShoppingCartItem>

    fun findByIdAndUserId(id: Long, userId: String): Optional<ShoppingCartItem>

    fun deleteByIdAndUserId(id: Long, userId: String)

    fun deleteAllByUserId(userId: String)
}