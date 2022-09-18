package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.ProductCategory
import com.jorgeparavicini.springwebshop.database.repositories.ProductCategoryRepository
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.models.ProductCategoryDTO
import org.springframework.stereotype.Service

@Service
class ProductCategoryServiceImpl(override val repo: ProductCategoryRepository) : ProductCategoryService {
    override fun ProductCategory.toTDto() = ProductCategoryDTO(id!!, name, description)

    override fun ProductCategoryDTO.toEntity() = ProductCategory(name, description, emptyList(), id)
}