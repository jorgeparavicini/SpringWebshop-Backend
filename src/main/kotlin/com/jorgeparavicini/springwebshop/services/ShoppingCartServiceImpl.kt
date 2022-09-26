package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.ShoppingCartItem
import com.jorgeparavicini.springwebshop.database.repositories.ProductRepository
import com.jorgeparavicini.springwebshop.database.repositories.ShoppingCartItemRepository
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.exceptions.ForbiddenException
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.exceptions.UnauthorizedException
import com.jorgeparavicini.springwebshop.dto.ShoppingCartItemDTO
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class ShoppingCartServiceImpl(
    override val repo: ShoppingCartItemRepository,
    private val productRepo: ProductRepository
) : ShoppingCartService {

    private val userId: String
        get() = SecurityContextHolder.getContext()?.authentication?.name ?: throw UnauthorizedException()

    override fun ShoppingCartItem.toDto(): ShoppingCartItemDTO = ShoppingCartItemDTO(id!!, product.id!!, quantity)

    override fun ShoppingCartItemDTO.toEntity(): ShoppingCartItem {
        val product = productRepo.findByIdOrNull(productId)
            ?: throw NotFoundException("Could not find product with id $productId")
        return ShoppingCartItem(product, quantity, userId, id)
    }

    override fun getAll(): Iterable<ShoppingCartItemDTO> {
        return repo.findByUserId(userId).map { it.toDto() }
    }

    override fun find(id: Long): ShoppingCartItemDTO {
        return repo.findByIdAndUserId(id, userId)
            .orElseThrow { NotFoundException("Could not find shopping cart item with id $id for the current user") }
            .toDto()
    }

    override fun update(id: Long, newEntity: ShoppingCartItemDTO): ShoppingCartItemDTO {
        if (id != newEntity.id)
            throw BadRequestException("The passed id ($id) does not match the id of the entity: ${newEntity.id}")
        val entity = repo.findByIdOrNull(id)
        if (entity != null && entity.userId != userId) {
            throw ForbiddenException("Unable to modify shopping cart of another user.")
        }
        return repo.save(newEntity.toEntity()).toDto()
    }

    override fun delete(id: Long) {
        repo.deleteByIdAndUserId(id, userId)
    }
}