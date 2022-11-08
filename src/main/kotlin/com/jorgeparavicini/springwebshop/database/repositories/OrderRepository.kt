package com.jorgeparavicini.springwebshop.database.repositories

import com.jorgeparavicini.springwebshop.database.entities.Order

interface OrderRepository : BaseRepository<Order> {
    fun getAllByUserId(userId: String): List<Order>

    fun findByIdAndUserId(id: Long, userId: String): Order?
}