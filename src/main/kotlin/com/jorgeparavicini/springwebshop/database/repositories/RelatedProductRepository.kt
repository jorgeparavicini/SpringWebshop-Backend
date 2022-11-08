package com.jorgeparavicini.springwebshop.database.repositories

import com.jorgeparavicini.springwebshop.database.entities.RelatedProduct

interface RelatedProductRepository : BaseRepository<RelatedProduct> {
    fun getRelatedProductByProductId(productId: Long): Iterable<RelatedProduct>
}