package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.*
import com.jorgeparavicini.springwebshop.database.repositories.*
import com.jorgeparavicini.springwebshop.dto.*
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.exceptions.UnauthorizedException
import org.springframework.data.domain.Pageable
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
    private val reviewRepo: ReviewRepository,
    private val securityService: SecurityService
) : ProductService {

    override fun Product.toDto() =
        ProductDTO(
            id!!,
            name,
            description,
            shippingPrice,
            oldPrice,
            thumbnailUri,
            price,
            category.id!!,
            vendor.id!!,
            repo.getAverageRating(id),
            repo.getNrOfRatings(id)
        )

    override fun ProductDTO.toEntity(): Product {
        val category = categoryRepo.findByIdOrNull(categoryId) ?: throw NotFoundException("Category not found")
        val vendor = vendorRepo.findByIdOrNull(vendorId) ?: throw NotFoundException("Vendor not found")
        return Product(name, description, price, shippingPrice, oldPrice, thumbnailUri, category, vendor, id)
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

        return Review(product, securityService.userId, rating, comment, id ?: 0)
    }

    override fun getAll(
        pageable: Pageable,
        category: Long,
        categories: List<Long>?,
        vendors: List<Long>?,
        minPrice: Float?,
        maxPrice: Float?,
        maxShippingPrice: Float?,
        minRating: Float?
    ): ProductListFilterInfo {
        val foundCategories = repo.getSubcategories(category)
        val products = repo.findAllBy(
            pageable,
            foundCategories.map { it.getId() },
            categories,
            vendors,
            minPrice,
            maxPrice,
            maxShippingPrice,
            minRating
        ).map { it.toDto() }
        val foundVendors = repo.getVendors(category)
        val simpleInfo = repo.getSimpleProductFilterInfo(category)
        val productFilterInfo =
            ProductFilterInfo(
                foundCategories.map {
                    ProductCategoryDTO(
                        it.getId(),
                        it.getName(),
                        it.getDescription(),
                        it.getIcon(),
                        it.getParentCategory()
                    )
                },
                foundVendors.map {
                    VendorDTO(
                        it.getId(),
                        it.getName(),
                        it.getDescription(),
                        it.getAddressId()
                    )
                },
                simpleInfo[0] as Float? ?: 0.0f,
                simpleInfo[1] as Float? ?: 0.0f,
                simpleInfo[2] as Float? ?: 0.0f,
            )
        return ProductListFilterInfo(products, productFilterInfo)
    }

    override fun getAllRelatedProducts(productId: Long): Iterable<RelatedProductDTO> {
        return relatedProductRepo.getRelatedProductByProductId(productId).map { it.toDto() }
    }

    override fun findRelatedProduct(productId: Long, relatedProductId: Long): RelatedProductDTO {
        relatedProductRepo.findByIdOrNull(relatedProductId)?.let {
            if (it.product.id != productId) throw BadRequestException("The passed id ($productId) does not match the id of the product: ${it.product.id}")
            return it.toDto()
        }
        throw NotFoundException("Could not find related product with id: $relatedProductId")
    }

    override fun createRelatedProduct(productId: Long, dto: RelatedProductDTO): RelatedProductDTO {
        if (productId != dto.productId) throw BadRequestException("The passed id ($productId) does not match the id of the product: ${dto.productId}")
        dto.id = 0
        val entity = dto.toEntity()
        return relatedProductRepo.save(entity).toDto()
    }

    override fun updateRelatedProduct(
        productId: Long, id: Long, dto: RelatedProductDTO
    ): RelatedProductDTO {
        if (productId != dto.productId) throw BadRequestException("The passed id ($productId) does not match the id of the product: ${dto.productId}")
        if (id != dto.id) throw BadRequestException("The passed id ($id) does not match the id of the entity: ${dto.productId}")
        val entity = dto.toEntity()
        return relatedProductRepo.save(entity).toDto()
    }

    override fun deleteRelatedProduct(productId: Long, relatedProductId: Long) {
        relatedProductRepo.findByIdOrNull(relatedProductId)?.let {
            if (it.product.id != productId) throw BadRequestException("The passed id ($productId) does not match the id of the product: ${it.product.id}")
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

        if (existingEntity.userId != securityService.userId) {
            throw BadRequestException("Can not update review from another user.")
        }

        val newEntity = createReviewDTO.toEntity(existingEntity.id!!)
        return reviewRepo.save(newEntity).toDto()
    }

    @Transactional
    override fun deleteReview(productId: Long, reviewId: Long) {
        val review = reviewRepo.findByIdAndProductId(reviewId, productId) ?: return
        if (review.userId != securityService.userId) throw UnauthorizedException("Can not delete review of another person")
        reviewRepo.softDelete(reviewId)
    }
}