package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.ShoppingCartItem
import com.jorgeparavicini.springwebshop.database.repositories.ShoppingCartItemRepository
import com.jorgeparavicini.springwebshop.dto.ShoppingCartItemDTO

interface ShoppingCartService : Service<ShoppingCartItem, ShoppingCartItemDTO, ShoppingCartItemRepository>