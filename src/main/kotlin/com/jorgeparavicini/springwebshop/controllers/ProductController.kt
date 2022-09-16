package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.database.entities.Product
import com.jorgeparavicini.springwebshop.database.repositories.ProductRepository
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/product"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductController(private val repo: ProductRepository) {
    @GetMapping
    fun getAll(): List<Product> {
        return repo.findAll().toList()
    }

    @GetMapping("{id}")
    fun find(@PathVariable id: Long): Product {
        return repo.findById(id).orElseThrow { NotFoundException("Product not found") }
    }

    @PostMapping
    fun create(@RequestBody newProduct: Product): Product {
        return repo.save(newProduct)
    }
}