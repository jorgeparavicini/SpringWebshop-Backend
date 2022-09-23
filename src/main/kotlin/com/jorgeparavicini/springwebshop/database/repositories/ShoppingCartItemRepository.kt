package com.jorgeparavicini.springwebshop.database.repositories

import com.jorgeparavicini.springwebshop.database.entities.ShoppingCartItem
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ShoppingCartItemRepository : CrudRepository<ShoppingCartItem, Long> {
    fun findByUserId(userId: String): List<ShoppingCartItem>

    fun findByIdAndUserId(id: Long, userId: String): Optional<ShoppingCartItem>

    fun deleteByIdAndUserId(id: Long, userId: String)
}