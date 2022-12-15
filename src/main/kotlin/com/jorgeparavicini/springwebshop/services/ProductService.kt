package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.Product
import com.jorgeparavicini.springwebshop.database.repositories.ProductRepository
import com.jorgeparavicini.springwebshop.dto.*
import org.springframework.data.domain.Pageable

interface ProductService : Service<Product, ProductDTO, ProductRepository> {
    fun getAll(
        pageable: Pageable,
        category: Long,
        categories: List<Long>?,
        vendors: List<Long>?,
        minPrice: Float?,
        maxPrice: Float?,
        maxShippingPrice: Float?,
        minRating: Float?
    ): ProductListFilterInfo

    fun getAllRelatedProducts(productId: Long): Iterable<RelatedProductDTO>

    fun findRelatedProduct(productId: Long, relatedProductId: Long): RelatedProductDTO

    fun createRelatedProduct(productId: Long, dto: RelatedProductDTO): RelatedProductDTO

    fun updateRelatedProduct(productId: Long, id: Long, dto: RelatedProductDTO): RelatedProductDTO

    fun deleteRelatedProduct(productId: Long, relatedProductId: Long)

    fun getAllReviews(productId: Long): Iterable<ReviewDTO>

    fun findReview(productId: Long, reviewId: Long): ReviewDTO

    fun createReview(productId: Long, createReviewDTO: CreateReviewDTO): ReviewDTO

    fun updateReview(productId: Long, reviewId: Long, createReviewDTO: CreateReviewDTO): ReviewDTO

    fun deleteReview(productId: Long, reviewId: Long)
}