package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.dto.ProductCategoryDTO
import com.jorgeparavicini.springwebshop.services.ProductCategoryService
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["api/product-categories"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductCategoryController(private val service: ProductCategoryService) {

    @GetMapping
    fun getAll(): List<ProductCategoryDTO> {
        return service.getAll().toList()
    }

    @GetMapping("{id}")
    fun find(@PathVariable id: Long): ProductCategoryDTO {
        return service.find(id)
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create:product-category')")
    fun create(@Valid @RequestBody newProductCategory: ProductCategoryDTO): ProductCategoryDTO {
        return service.create(newProductCategory)
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('update:product-category')")
    fun update(@PathVariable id: Long, @Valid @RequestBody newProductCategory: ProductCategoryDTO): ProductCategoryDTO {
        return service.update(id, newProductCategory)
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('delete:product-category')")
    fun delete(@PathVariable id: Long) {
        service.delete(id)
    }
}