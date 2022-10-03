package com.jorgeparavicini.springwebshop.database.repositories

import com.jorgeparavicini.springwebshop.database.entities.Product

interface ProductRepository : BaseRepository<Product> {
    fun findByCategoryIdIn(ids: Collection<Long>): Iterable<Product>

}