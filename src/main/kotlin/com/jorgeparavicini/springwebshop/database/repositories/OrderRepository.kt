package com.jorgeparavicini.springwebshop.database.repositories

import com.jorgeparavicini.springwebshop.database.entities.Order

interface OrderRepository : BaseRepository<Order> {
    fun getAllByUserIdOrderByDate(userId: String): List<Order>

    fun findByIdAndUserId(id: Long, userId: String): Order?
}