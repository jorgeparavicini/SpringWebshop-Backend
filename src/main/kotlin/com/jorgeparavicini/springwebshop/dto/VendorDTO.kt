package com.jorgeparavicini.springwebshop.dto

data class VendorDTO(
    override var id: Long,
    var name: String,
    var description: String,
    val addressId: Long
): DTO()