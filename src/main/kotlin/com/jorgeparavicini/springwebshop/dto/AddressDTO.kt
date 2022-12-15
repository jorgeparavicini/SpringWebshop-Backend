package com.jorgeparavicini.springwebshop.dto

import com.jorgeparavicini.springwebshop.database.entities.Address

data class AddressDTO(
    val id: Long,
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val country: String,
)
