package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.WishlistItem
import com.jorgeparavicini.springwebshop.database.repositories.ProductRepository
import com.jorgeparavicini.springwebshop.database.repositories.WishlistItemRepository
import com.jorgeparavicini.springwebshop.dto.WishlistItemDTO
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.exceptions.UnauthorizedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class WishlistServiceImpl(
    override val repo: WishlistItemRepository,
    private val productRepo: ProductRepository,
    private val securityService: SecurityService
) : WishlistService {

    override fun WishlistItem.toDto() = WishlistItemDTO(id!!, product.id!!)

    override fun WishlistItemDTO.toEntity(): WishlistItem {
        val product = productRepo.findByIdOrNull(productId)
            ?: throw NotFoundException("Could not find product with id $productId")
        return WishlistItem(product, securityService.userId, id)
    }

    override fun getAll(): Iterable<WishlistItemDTO> {
        return repo.findByUserId(securityService.userId).map { it.toDto() }
    }

    override fun find(id: Long): WishlistItemDTO {
        return repo.findByIdAndUserId(id, securityService.userId)
            .orElseThrow { NotFoundException("Could not find wishlist item with id $id for the current user") }
            .toDto()
    }

    @Transactional
    override fun delete(id: Long) {
        val wishlistItem = repo.findByIdOrNull(id) ?: return
        if (wishlistItem.userId != securityService.userId) throw UnauthorizedException("Can not delete item from another users wishlist")
        repo.softDelete(id)
    }
}