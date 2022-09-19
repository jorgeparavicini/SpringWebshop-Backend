package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.Product
import com.jorgeparavicini.springwebshop.database.repositories.ProductRepository
import com.jorgeparavicini.springwebshop.models.ProductDTO
import com.jorgeparavicini.springwebshop.models.RelatedProductDTO

interface ProductService : Service<Product, ProductDTO, ProductRepository> {
    fun getAllRelatedProducts(productId: Long): Iterable<RelatedProductDTO>

    fun findRelatedProduct(productId: Long, relatedProductId: Long): RelatedProductDTO

    fun createRelatedProduct(productId: Long, dto: RelatedProductDTO): RelatedProductDTO

    fun updateRelatedProduct(productId: Long, id: Long, dto: RelatedProductDTO): RelatedProductDTO

    fun deleteRelatedProduct(productId: Long, relatedProductId: Long)
}