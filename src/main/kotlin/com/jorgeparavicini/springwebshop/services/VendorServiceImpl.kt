package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.Vendor
import com.jorgeparavicini.springwebshop.database.repositories.AddressRepository
import com.jorgeparavicini.springwebshop.database.repositories.VendorRepository
import com.jorgeparavicini.springwebshop.dto.VendorDTO
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.webjars.NotFoundException

@Service
class VendorServiceImpl(
    override val repo: VendorRepository,
    private val addressRepo: AddressRepository
) : VendorService {

    override fun Vendor.toDto() = VendorDTO(id!!, name, description, address.id!!)

    override fun VendorDTO.toEntity(): Vendor {
        val address = addressRepo.findByIdOrNull(addressId) ?: throw NotFoundException("Address not found");
        return Vendor(name, description, address)
    }
}