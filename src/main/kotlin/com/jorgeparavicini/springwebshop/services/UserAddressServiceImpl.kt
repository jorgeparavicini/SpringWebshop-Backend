package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.Address
import com.jorgeparavicini.springwebshop.database.entities.UserAddress
import com.jorgeparavicini.springwebshop.database.repositories.AddressRepository
import com.jorgeparavicini.springwebshop.database.repositories.UserAddressRepository
import com.jorgeparavicini.springwebshop.dto.AddressDTO
import com.jorgeparavicini.springwebshop.dto.UserAddressDTO
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.exceptions.UnauthorizedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserAddressServiceImpl(
    val repo: UserAddressRepository,
    val addressRepo: AddressRepository
) : UserAddressService {
    private val userId: String
        get() = SecurityContextHolder.getContext()?.authentication?.name ?: throw UnauthorizedException()

    private fun Address.toDto() = AddressDTO(id!!, street, city, state, zipCode, country)

    private fun AddressDTO.toEntity(): Address {
        return Address(street, city, state, zipCode, country)
    }

    fun UserAddress.toDto() = UserAddressDTO(id!!, name, address.toDto())

    override fun getAll(): Iterable<UserAddressDTO> {
        return repo.getAllByUserId(userId).map { it.toDto() }
    }

    override fun find(id: Long): UserAddressDTO {
        return repo.findByUserIdAndId(userId, id)
            .orElseThrow { NotFoundException("User address not found") }
            .toDto()
    }

    @Transactional
    override fun create(newEntity: UserAddressDTO): UserAddressDTO {
        val address = addressRepo.save(newEntity.address.toEntity());
        val userAddress = UserAddress(newEntity.name, userId, address)
        return repo.save(userAddress).toDto()
    }

    @Transactional
    override fun update(id: Long, newEntity: UserAddressDTO): UserAddressDTO {
        val userAddress = repo.findByUserIdAndId(userId, id).orElseThrow { NotFoundException("User address not found") }
        val address = Address(
            newEntity.address.street,
            newEntity.address.city,
            newEntity.address.state,
            newEntity.address.zipCode,
            newEntity.address.country,
            userAddress.id
        )
        val savedAddress = addressRepo.save(address)
        val updatedUserAddress = UserAddress(newEntity.name, userId, savedAddress, id)
        return repo.save(updatedUserAddress).toDto()
    }

    @Transactional
    override fun delete(id: Long) {
        return repo.softDelete(id, userId)
    }

}