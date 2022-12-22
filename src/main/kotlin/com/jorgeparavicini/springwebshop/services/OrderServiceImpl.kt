package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.Order
import com.jorgeparavicini.springwebshop.database.entities.OrderItem
import com.jorgeparavicini.springwebshop.database.entities.ShoppingCartItem
import com.jorgeparavicini.springwebshop.database.repositories.*
import com.jorgeparavicini.springwebshop.dto.CreateOrderDTO
import com.jorgeparavicini.springwebshop.dto.OrderDTO
import com.jorgeparavicini.springwebshop.dto.OrderItemDTO
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.exceptions.UnauthorizedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class OrderServiceImpl(
    private val repo: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val shoppingCartRepo: ShoppingCartItemRepository,
    private val addressRepo: AddressRepository,
    private val securityService: SecurityService
) : OrderService {

    private fun OrderItem.toDto() = OrderItemDTO(id!!, product.id!!, quantity)

    private fun Order.toDto(): OrderDTO {
        return OrderDTO(
            id!!,
            comments,
            address.id!!,
            orderItems.map { it.toDto() }.toSet(),
            date,
            orderItems.sumOf { (it.product.price * it.quantity).toDouble() }
        )
    }

    private fun ShoppingCartItem.toOrderItem() =
        OrderItem(product, quantity)

    private fun CreateOrderDTO.toEntity(): Order {
        val orderItems = shoppingCartRepo.findByUserId(securityService.userId).map { it.toOrderItem() }.toSet()
        val address = addressRepo.findByIdOrNull(address_id) ?: throw NotFoundException("Address not found")
        return Order(comments, address, orderItems, securityService.userId, LocalDateTime.now())
    }

    override fun getAll(): List<OrderDTO> {
        return repo.getAllByUserIdOrderByDate(securityService.userId).map { it.toDto() }
    }

    override fun find(id: Long): OrderDTO {
        return repo.findByIdAndUserId(id, securityService.userId)?.toDto() ?: throw NotFoundException("Could not find order")
    }

    @Transactional
    override fun create(dto: CreateOrderDTO): OrderDTO {
        val entity = dto.toEntity()
        if (entity.orderItems.isEmpty()) {
            throw BadRequestException("Can not order an empty shopping cart.")
        }

        orderItemRepository.saveAll(entity.orderItems);
        val result = repo.save(entity).toDto()
        shoppingCartRepo.deleteAllByUserId(securityService.userId)
        return result
    }
}