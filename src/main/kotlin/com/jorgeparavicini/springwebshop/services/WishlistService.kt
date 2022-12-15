package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.WishlistItem
import com.jorgeparavicini.springwebshop.database.repositories.WishlistItemRepository
import com.jorgeparavicini.springwebshop.dto.WishlistItemDTO

interface WishlistService : Service<WishlistItem, WishlistItemDTO, WishlistItemRepository>