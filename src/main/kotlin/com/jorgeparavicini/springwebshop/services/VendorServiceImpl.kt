package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.Vendor
import com.jorgeparavicini.springwebshop.database.repositories.VendorRepository
import com.jorgeparavicini.springwebshop.dto.VendorDTO
import org.springframework.stereotype.Service

@Service
class VendorServiceImpl(override val repo: VendorRepository) : VendorService {

    override fun Vendor.toDto() = VendorDTO(id!!, name, description, street, city, postalCode, country)

    override fun VendorDTO.toEntity() = Vendor(name, description, street, city, postalCode, country)
}