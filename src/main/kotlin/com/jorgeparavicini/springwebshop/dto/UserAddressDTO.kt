package com.jorgeparavicini.springwebshop.dto

data class UserAddressDTO(
    override var id: Long,
    val name: String,
    val address: AddressDTO
) : DTO()
