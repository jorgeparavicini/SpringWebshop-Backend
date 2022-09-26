package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.ProductCategory
import com.jorgeparavicini.springwebshop.database.repositories.ProductCategoryRepository
import com.jorgeparavicini.springwebshop.dto.ProductCategoryDTO
import org.springframework.stereotype.Service

@Service
class ProductCategoryServiceImpl(override val repo: ProductCategoryRepository) : ProductCategoryService {
    override fun ProductCategory.toDto() = ProductCategoryDTO(id!!, name, description)

    override fun ProductCategoryDTO.toEntity() = ProductCategory(name, description, emptyList(), id)
}