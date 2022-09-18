package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.Product
import com.jorgeparavicini.springwebshop.database.repositories.ProductCategoryRepository
import com.jorgeparavicini.springwebshop.database.repositories.ProductRepository
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.models.ProductDTO
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(override val repo: ProductRepository, private val categoryRepo: ProductCategoryRepository) :
    ProductService {

    override fun Product.toTDto() = ProductDTO(id!!, name, description, price, category.id!!)

    override fun ProductDTO.toEntity(): Product {
        val category = categoryRepo.findByIdOrNull(categoryId) ?: throw NotFoundException("Category not found")
        return Product(name, description, price, category, id)
    }
}