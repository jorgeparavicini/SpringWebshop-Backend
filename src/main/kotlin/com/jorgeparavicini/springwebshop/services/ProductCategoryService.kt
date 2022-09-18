package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.ProductCategory
import com.jorgeparavicini.springwebshop.database.repositories.ProductCategoryRepository
import com.jorgeparavicini.springwebshop.models.ProductCategoryDTO

interface ProductCategoryService : Service<ProductCategory, ProductCategoryDTO, ProductCategoryRepository>