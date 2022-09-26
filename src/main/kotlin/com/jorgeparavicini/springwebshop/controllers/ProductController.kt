package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.models.CreateReviewDTO
import com.jorgeparavicini.springwebshop.models.ProductDTO
import com.jorgeparavicini.springwebshop.models.RelatedProductDTO
import com.jorgeparavicini.springwebshop.models.ReviewDTO
import com.jorgeparavicini.springwebshop.services.ProductService
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["api/products"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductController(private val service: ProductService) {
    @GetMapping
    fun getAll(): List<ProductDTO> {
        return service.getAll().toList()
    }

    @GetMapping("{id}")
    fun find(@PathVariable id: Long): ProductDTO {
        return service.find(id)
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create:product')")
    fun create(@RequestBody newProduct: ProductDTO): ProductDTO {
        return service.create(newProduct)
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('update:product')")
    fun update(@PathVariable id: Long, @RequestBody newProduct: ProductDTO): ProductDTO {
        return service.update(id, newProduct)
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('delete:product')")
    fun delete(@PathVariable id: Long) {
        service.delete(id)
    }

    // Related products
    @GetMapping("{id}/related")
    fun getAllRelated(@PathVariable id: Long): List<RelatedProductDTO> {
        return service.getAllRelatedProducts(id).toList()
    }

    @GetMapping("{id}/related/{relatedId}")
    fun getRelatedProduct(@PathVariable id: Long, @PathVariable relatedId: Long): RelatedProductDTO {
        return service.findRelatedProduct(id, relatedId)
    }

    @PostMapping("{id}/related")
    @PreAuthorize("hasAuthority('create:related-product')")
    fun createRelatedProduct(@PathVariable id: Long, @RequestBody dto: RelatedProductDTO): RelatedProductDTO {
        return service.createRelatedProduct(id, dto)
    }

    @PutMapping("{id}/related/{relatedId}")
    @PreAuthorize("hasAuthority('update:related-product')")
    fun updateRelatedProduct(
        @PathVariable id: Long,
        @PathVariable relatedId: Long,
        dto: RelatedProductDTO
    ): RelatedProductDTO {
        return service.updateRelatedProduct(id, relatedId, dto)
    }

    @DeleteMapping("{id}/related/{relatedId}")
    @PreAuthorize("hasAuthority('delete:related-product')")
    fun deleteRelatedProduct(@PathVariable id: Long, @PathVariable relatedId: Long) {
        service.deleteRelatedProduct(id, relatedId)
    }

    // Reviews

    @GetMapping("{id}/reviews")
    fun getReviews(@PathVariable id: Long): List<ReviewDTO> {
        return service.getAllReviews(id).toList()
    }

    @GetMapping("{id}/reviews/{reviewId}")
    fun findReview(@PathVariable id: Long, @PathVariable reviewId: Long): ReviewDTO {
        return service.findReview(id, reviewId)
    }

    @PostMapping("{id}/reviews")
    fun createReview(@PathVariable id: Long, @RequestBody reviewDTO: CreateReviewDTO): ReviewDTO {
        return service.createReview(id, reviewDTO)
    }

    @PutMapping("{id}/reviews/{reviewId}")
    fun updateReview(
        @PathVariable id: Long,
        @PathVariable reviewId: Long,
        @RequestBody reviewDTO: CreateReviewDTO
    ): ReviewDTO {
        return service.updateReview(id, reviewId, reviewDTO)
    }

    @DeleteMapping("{id}/reviews/{reviewId}")
    fun deleteReview(@PathVariable id: Long, @PathVariable reviewId: Long) {
        service.deleteReview(id, reviewId)
    }
}