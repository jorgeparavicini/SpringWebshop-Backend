package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.Product
import com.jorgeparavicini.springwebshop.database.repositories.ProductRepository
import com.jorgeparavicini.springwebshop.models.ProductDTO

interface ProductService : Service<Product, ProductDTO, ProductRepository>