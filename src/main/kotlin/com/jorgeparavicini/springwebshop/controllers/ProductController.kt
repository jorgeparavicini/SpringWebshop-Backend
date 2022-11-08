package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.dto.*
import com.jorgeparavicini.springwebshop.services.ProductService
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["api/products"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductController(private val service: ProductService) {
    @GetMapping
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") pageSize: Int,
        @RequestParam category: Long,
        @RequestParam filteredCategories: List<Long>?,
        @RequestParam filteredVendors: List<Long>?,
        @RequestParam minPrice: Float?,
        @RequestParam maxPrice: Float?,
        @RequestParam maxShippingPrice: Float?,
        @RequestParam minRating: Int?
    ): ProductListFilterInfo {
        val pageable = PageRequest.of(page, pageSize)
        return service.getAll(
            pageable,
            category,
            filteredCategories,
            filteredVendors,
            minPrice,
            maxPrice,
            maxShippingPrice,
            minRating
        )
    }

    @GetMapping("{id}")
    fun find(@PathVariable id: Long): ProductDTO {
        return service.find(id)
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create:product')")
    fun create(@Valid @RequestPart("dto") newProduct: ProductDTO): ProductDTO {
        return service.create(newProduct)
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('update:product')")
    fun update(@PathVariable id: Long, @Valid @RequestBody newProduct: ProductDTO): ProductDTO {
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
    fun createRelatedProduct(@PathVariable id: Long, @Valid @RequestBody dto: RelatedProductDTO): RelatedProductDTO {
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
    fun createReview(@PathVariable id: Long, @Valid @RequestBody reviewDTO: CreateReviewDTO): ReviewDTO {
        return service.createReview(id, reviewDTO)
    }

    @PutMapping("{id}/reviews/{reviewId}")
    fun updateReview(
        @PathVariable id: Long,
        @PathVariable reviewId: Long,
        @Valid @RequestBody reviewDTO: CreateReviewDTO
    ): ReviewDTO {
        return service.updateReview(id, reviewId, reviewDTO)
    }

    @DeleteMapping("{id}/reviews/{reviewId}")
    fun deleteReview(@PathVariable id: Long, @PathVariable reviewId: Long) {
        service.deleteReview(id, reviewId)
    }
}