package com.jorgeparavicini.springwebshop.database.repositories

import com.jorgeparavicini.springwebshop.database.entities.OrderItem
import org.springframework.data.repository.CrudRepository

interface OrderItemRepository : CrudRepository<OrderItem, Long>