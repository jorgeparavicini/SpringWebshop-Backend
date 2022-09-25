package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.Order
import com.jorgeparavicini.springwebshop.database.entities.OrderItem
import com.jorgeparavicini.springwebshop.database.entities.ShoppingCartItem
import com.jorgeparavicini.springwebshop.database.repositories.OrderRepository
import com.jorgeparavicini.springwebshop.database.repositories.ShoppingCartItemRepository
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.exceptions.UnauthorizedException
import com.jorgeparavicini.springwebshop.models.CreateOrderDTO
import com.jorgeparavicini.springwebshop.models.OrderDTO
import com.jorgeparavicini.springwebshop.models.OrderItemDTO
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class OrderServiceImpl(
    private val repo: OrderRepository,
    private val shoppingCartRepo: ShoppingCartItemRepository
) : OrderService {
    private val userId: String
        get() = SecurityContextHolder.getContext()?.authentication?.name ?: throw UnauthorizedException()

    private fun OrderItem.toDto() = OrderItemDTO(id!!, product.id!!, quantity)

    private fun Order.toDto() =
        OrderDTO(id!!, comments, street, city, postalCode, country, orderItems.map { it.toDto() }.toSet())

    private fun ShoppingCartItem.toOrderItem() =
        OrderItem(product, quantity, 0)

    private fun CreateOrderDTO.toEntity(): Order {
        val orderItems = shoppingCartRepo.findByUserId(userId).map { it.toOrderItem() }.toSet()
        return Order(comments, street, city, postalCode, country, orderItems, userId, id)
    }

    override fun getAll(): List<OrderDTO> {
        return repo.getAllByUserId(userId).map { it.toDto() }
    }

    override fun find(id: Long): OrderDTO {
        return repo.findByIdAndUserId(id, userId)?.toDto() ?: throw NotFoundException("Could not find order")
    }

    @Transactional
    override fun create(dto: CreateOrderDTO): OrderDTO {
        dto.id = 0
        val entity = dto.toEntity()
        if (entity.orderItems.isEmpty()) {
            throw BadRequestException("Can not order an empty shopping cart.")
        }

        val result = repo.save(entity).toDto()
        shoppingCartRepo.deleteAllByUserId(userId)
        return result
    }
}