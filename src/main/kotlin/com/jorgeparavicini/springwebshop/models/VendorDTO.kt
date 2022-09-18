package com.jorgeparavicini.springwebshop.models

data class VendorDTO(
    override var id: Long,
    var name: String,
    var description: String,
    var street: String,
    var city: String,
    var postalCode: String,
    var country: String
): DTO()