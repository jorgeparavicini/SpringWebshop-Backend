package com.jorgeparavicini.springwebshop.models

data class CreateOrderDTO(
    override var id: Long,
    var comments: String,
    var street: String,
    var city: String,
    var postalCode: String,
    var country: String,
) : DTO()