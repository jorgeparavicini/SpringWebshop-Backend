package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.ProductCategory
import com.jorgeparavicini.springwebshop.database.repositories.ProductCategoryRepository
import com.jorgeparavicini.springwebshop.dto.ProductCategoryDTO
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductCategoryServiceImpl(override val repo: ProductCategoryRepository) : ProductCategoryService {
    override fun ProductCategory.toDto() = ProductCategoryDTO(id!!, name, description, parentCategory?.id)

    override fun ProductCategoryDTO.toEntity(): ProductCategory {
        val category = parentCategory?.let {
            repo.findByIdOrNull(it)
                ?: throw NotFoundException("Could not find parent category with id: $parentCategory")
        }
        return ProductCategory(name, description, category, emptyList(), emptyList(), id)
    }
}