package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.ProductCategory
import com.jorgeparavicini.springwebshop.database.repositories.ProductCategoryRepository
import com.jorgeparavicini.springwebshop.dto.ProductCategoryDTO
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class ProductCategoryServiceImpl(override val repo: ProductCategoryRepository) : ProductCategoryService {
    override fun ProductCategory.toDto() = ProductCategoryDTO(id!!, name, description, icon, parentCategory?.id)

    override fun ProductCategoryDTO.toEntity(): ProductCategory {
        val category = parentCategory?.let {
            repo.findByIdOrNull(it)
                ?: throw NotFoundException("Could not find parent category with id: $parentCategory")
        }
        return ProductCategory(name, description, icon, category, emptyList(), emptyList(), id)
    }

    override fun delete(id: Long) {
        try {
            super.delete(id)
        } catch (exception: DataIntegrityViolationException) {
            throw BadRequestException("Can not delete category as products depend on it.")
        }
    }
}