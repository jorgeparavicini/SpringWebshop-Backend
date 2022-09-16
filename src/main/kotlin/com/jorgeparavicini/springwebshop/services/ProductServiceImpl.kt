package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.Product
import com.jorgeparavicini.springwebshop.database.repositories.ProductCategoryRepository
import com.jorgeparavicini.springwebshop.database.repositories.ProductRepository
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.models.ProductDTO
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(private val repo: ProductRepository, private val categoryRepo: ProductCategoryRepository) :
    ProductService {

    private fun Product.toProductDTO() = ProductDTO(
        id!!, name, description, price, category.id!!
    )

    private fun ProductDTO.toProduct(): Product {
        val category = categoryRepo.findByIdOrNull(categoryId) ?: throw NotFoundException("Category not found")
        return Product(name, description, price, category, id)
    }

    override fun find(id: Long): ProductDTO {
        return repo.findById(id).orElseThrow { NotFoundException("Product not found") }.toProductDTO()
    }

    override fun getAll(): Iterable<ProductDTO> {
        return repo.findAll().map { it.toProductDTO() }
    }

    override fun create(newEntity: ProductDTO): ProductDTO {
        val entity = newEntity.toProduct()
        return repo.save(entity).toProductDTO()
    }

    override fun update(id: Long, newEntity: ProductDTO): ProductDTO {
        if (id != newEntity.id) throw BadRequestException("Id mismatch")
        val entity = newEntity.toProduct()
        return repo.save(entity).toProductDTO()
    }

    override fun delete(id: Long) {
        repo.deleteById(id)
    }
}