package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.Product
import com.jorgeparavicini.springwebshop.database.entities.ProductCategory
import com.jorgeparavicini.springwebshop.database.entities.RelatedProduct
import com.jorgeparavicini.springwebshop.database.entities.Review
import com.jorgeparavicini.springwebshop.database.repositories.*
import com.jorgeparavicini.springwebshop.dto.CreateReviewDTO
import com.jorgeparavicini.springwebshop.dto.ProductDTO
import com.jorgeparavicini.springwebshop.dto.RelatedProductDTO
import com.jorgeparavicini.springwebshop.dto.ReviewDTO
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.exceptions.UnauthorizedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ProductServiceImpl(
    override val repo: ProductRepository,
    private val categoryRepo: ProductCategoryRepository,
    private val vendorRepo: VendorRepository,
    private val relatedProductRepo: RelatedProductRepository,
    private val reviewRepo: ReviewRepository
) : ProductService {

    private val userId: String
        get() = SecurityContextHolder.getContext()?.authentication?.name ?: throw UnauthorizedException()

    override fun Product.toDto() = ProductDTO(id!!, name, description, price, category.id!!, vendor.id!!)

    private fun ProductCategory.categoriesId(): Collection<Long> {
        val queue = ArrayDeque(listOf(this))
        val resultSet = mutableSetOf<Long>()
        while (!queue.isEmpty()) {
            val current = queue.removeLast()
            resultSet.add(current.id!!)
            queue.addAll(current.subCategories)
        }

        return resultSet
    }

    override fun ProductDTO.toEntity(): Product {
        val category = categoryRepo.findByIdOrNull(categoryId) ?: throw NotFoundException("Category not found")
        val vendor = vendorRepo.findByIdOrNull(vendorId) ?: throw NotFoundException("Vendor not found")
        return Product(name, description, price, category, vendor, id)
    }

    private fun RelatedProduct.toDto() = RelatedProductDTO(id!!, product.id!!, relatedProduct.id!!, relevance)

    private fun RelatedProductDTO.toEntity(): RelatedProduct {
        val product =
            repo.findById(productId).orElseThrow { NotFoundException("Could not find product with id: $productId") }
        val relatedProduct = repo.findById(relatedProductId)
            .orElseThrow { NotFoundException("Could not find related product with id: $productId") }
        return RelatedProduct(product, relatedProduct, relevance, id)
    }

    private fun Review.toDto() = ReviewDTO(id!!, product.id!!, userId, rating, comment)

    private fun CreateReviewDTO.toEntity(id: Long? = null): Review {
        val product =
            repo.findById(productId).orElseThrow { NotFoundException("Could not find product with id: $productId") }

        return Review(product, userId, rating, comment, id ?: 0)
    }

    override fun getAll(categoryId: Long?): Iterable<ProductDTO> {
        return categoryId?.let { category ->
            val productCategory =
                categoryRepo.findByIdOrNull(category)
                    ?: throw NotFoundException("Could not find category with id $category")
            repo.findByCategoryIdIn(productCategory.categoriesId()).map { it.toDto() }
        } ?: run {
            repo.findAll().map { it.toDto() }
        }
    }

    override fun getAllRelatedProducts(productId: Long): Iterable<RelatedProductDTO> {
        return relatedProductRepo.getRelatedProductByProductId(productId).map { it.toDto() }
    }

    override fun findRelatedProduct(productId: Long, relatedProductId: Long): RelatedProductDTO {
        relatedProductRepo.findByIdOrNull(relatedProductId)?.let {
            if (it.product.id != productId)
                throw BadRequestException("The passed id ($productId) does not match the id of the product: ${it.product.id}")
            return it.toDto()
        }
        throw NotFoundException("Could not find related product with id: $relatedProductId")
    }

    override fun createRelatedProduct(productId: Long, dto: RelatedProductDTO): RelatedProductDTO {
        if (productId != dto.productId)
            throw BadRequestException("The passed id ($productId) does not match the id of the product: ${dto.productId}")
        dto.id = 0
        val entity = dto.toEntity()
        return relatedProductRepo.save(entity).toDto()
    }

    override fun updateRelatedProduct(
        productId: Long, id: Long, dto: RelatedProductDTO
    ): RelatedProductDTO {
        if (productId != dto.productId)
            throw BadRequestException("The passed id ($productId) does not match the id of the product: ${dto.productId}")
        if (id != dto.id)
            throw BadRequestException("The passed id ($id) does not match the id of the entity: ${dto.productId}")
        val entity = dto.toEntity()
        return relatedProductRepo.save(entity).toDto()
    }

    override fun deleteRelatedProduct(productId: Long, relatedProductId: Long) {
        relatedProductRepo.findByIdOrNull(relatedProductId)?.let {
            if (it.product.id != productId)
                throw BadRequestException("The passed id ($productId) does not match the id of the product: ${it.product.id}")
            relatedProductRepo.softDelete(relatedProductId)
        }
    }

    override fun getAllReviews(productId: Long): Iterable<ReviewDTO> {
        return reviewRepo.findAllByProductId(productId).map { it.toDto() }
    }

    override fun findReview(productId: Long, reviewId: Long): ReviewDTO {
        return reviewRepo.findByIdAndProductId(reviewId, productId)?.toDto()
            ?: throw NotFoundException("Could not find review with id $reviewId for product with id $productId")
    }

    override fun createReview(productId: Long, createReviewDTO: CreateReviewDTO): ReviewDTO {
        val entity = createReviewDTO.toEntity()
        if (entity.product.id != productId) {
            throw BadRequestException("Cannot create review for another product.")
        }

        return reviewRepo.save(entity).toDto()
    }

    override fun updateReview(productId: Long, reviewId: Long, createReviewDTO: CreateReviewDTO): ReviewDTO {
        if (createReviewDTO.productId != productId) {
            throw BadRequestException("Product id mismatch")
        }

        val existingEntity = reviewRepo.findByIdAndProductId(reviewId, productId)
            ?: throw NotFoundException("Could not find review with id $reviewId for product with id $productId")

        if (existingEntity.userId != userId) {
            throw BadRequestException("Can not update review from another user.")
        }

        val newEntity = createReviewDTO.toEntity(existingEntity.id!!)
        return reviewRepo.save(newEntity).toDto()
    }

    @Transactional
    override fun deleteReview(productId: Long, reviewId: Long) {
        val review = reviewRepo.findByIdAndProductId(reviewId, productId) ?: return
        if (review.userId != userId) throw UnauthorizedException("Can not delete review of another person")
        reviewRepo.softDelete(reviewId)
    }
}