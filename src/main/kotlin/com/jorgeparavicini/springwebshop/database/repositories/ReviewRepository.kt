package com.jorgeparavicini.springwebshop.database.repositories

import com.jorgeparavicini.springwebshop.database.entities.Review
import org.springframework.data.repository.CrudRepository

interface ReviewRepository : CrudRepository<Review, Long> {
    fun findAllByProductId(productId: Long): Iterable<Review>

    fun findByIdAndProductId(id: Long, productId: Long): Review?

    fun deleteByIdAndProductId(id: Long, productId: Long)
}