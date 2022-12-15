package com.jorgeparavicini.springwebshop.database.repositories

import com.jorgeparavicini.springwebshop.database.entities.WishlistItem
import java.util.*

interface WishlistItemRepository : BaseRepository<WishlistItem> {
    fun findByUserId(userId: String): List<WishlistItem>

    fun findByIdAndUserId(id: Long, userId: String): Optional<WishlistItem>

    fun deleteAllByUserId(userId: String)
}