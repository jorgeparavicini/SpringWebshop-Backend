package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.Product
import com.jorgeparavicini.springwebshop.database.entities.RelatedProduct
import com.jorgeparavicini.springwebshop.database.repositories.ProductCategoryRepository
import com.jorgeparavicini.springwebshop.database.repositories.ProductRepository
import com.jorgeparavicini.springwebshop.database.repositories.RelatedProductRepository
import com.jorgeparavicini.springwebshop.database.repositories.VendorRepository
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.models.ProductDTO
import com.jorgeparavicini.springwebshop.models.RelatedProductDTO
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(
    override val repo: ProductRepository,
    private val categoryRepo: ProductCategoryRepository,
    private val vendorRepo: VendorRepository,
    private val relatedProductRepo: RelatedProductRepository
) : ProductService {

    override fun Product.toTDto() = ProductDTO(id!!, name, description, price, category.id!!, vendor.id!!)

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
            relatedProductRepo.deleteById(relatedProductId)
        }
    }
}