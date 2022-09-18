package com.jorgeparavicini.springwebshop.database.repositories

import com.jorgeparavicini.springwebshop.database.entities.ProductCategory
import org.springframework.data.repository.CrudRepository

interface ProductCategoryRepository : CrudRepository<ProductCategory, Long>