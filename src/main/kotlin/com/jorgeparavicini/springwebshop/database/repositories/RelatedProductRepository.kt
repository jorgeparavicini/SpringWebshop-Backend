package com.jorgeparavicini.springwebshop.database.repositories

import com.jorgeparavicini.springwebshop.database.entities.RelatedProduct
import org.springframework.data.repository.CrudRepository

interface RelatedProductRepository : CrudRepository<RelatedProduct, Long> {
    fun getRelatedProductByProductId(productId: Long): Iterable<RelatedProduct>
}