package com.jorgeparavicini.springwebshop.database.repositories

import com.jorgeparavicini.springwebshop.database.entities.Order
import org.springframework.data.repository.CrudRepository

interface OrderRepository : CrudRepository<Order, Long> {
    fun getAllByUserId(userId: String): List<Order>

    fun findByIdAndUserId(id: Long, userId: String): Order?
}