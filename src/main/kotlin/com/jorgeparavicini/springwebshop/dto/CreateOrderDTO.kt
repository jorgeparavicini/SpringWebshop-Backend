package com.jorgeparavicini.springwebshop.dto

data class CreateOrderDTO(
    val comments: String,
    val address_id: Long
)