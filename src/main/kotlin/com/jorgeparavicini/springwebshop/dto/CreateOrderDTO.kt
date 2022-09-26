package com.jorgeparavicini.springwebshop.dto

data class CreateOrderDTO(
    var comments: String,
    var street: String,
    var city: String,
    var postalCode: String,
    var country: String,
)