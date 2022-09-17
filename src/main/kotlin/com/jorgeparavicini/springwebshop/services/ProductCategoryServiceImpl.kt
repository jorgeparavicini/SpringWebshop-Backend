package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.ProductCategory
import com.jorgeparavicini.springwebshop.database.repositories.ProductCategoryRepository
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.models.ProductCategoryDTO
import org.springframework.stereotype.Service

@Service
class ProductCategoryServiceImpl(private val repo: ProductCategoryRepository) : ProductCategoryService {

    private fun ProductCategoryDTO.toProductCategory(): ProductCategory {
        return ProductCategory(name, description, emptyList(), id)
    }

    private fun ProductCategory.toProductCategoryDTO() = ProductCategoryDTO(
        id!!, name, description
    )

    override fun find(id: Long): ProductCategoryDTO {
        return repo.findById(id).orElseThrow {NotFoundException("Product Category not found")}.toProductCategoryDTO()
    }

    override fun getAll(): Iterable<ProductCategoryDTO> {
        return repo.findAll().map { it.toProductCategoryDTO() }
    }

    override fun create(newEntity: ProductCategoryDTO): ProductCategoryDTO {
        val entity = newEntity.toProductCategory()
        return repo.save(entity).toProductCategoryDTO()
    }

    override fun update(id: Long, newEntity: ProductCategoryDTO): ProductCategoryDTO {
        if (id != newEntity.id) throw BadRequestException("Id mismatch")
        val entity = newEntity.toProductCategory()
        return repo.save(entity).toProductCategoryDTO()
    }

    override fun delete(id: Long) {
        repo.deleteById(id)
    }
}