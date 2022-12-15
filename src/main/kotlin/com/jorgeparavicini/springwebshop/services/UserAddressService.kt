package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.dto.UserAddressDTO

interface UserAddressService {
    fun getAll(): Iterable<UserAddressDTO>

    fun find(id: Long): UserAddressDTO

    fun create(newEntity: UserAddressDTO): UserAddressDTO

    fun update(id: Long, newEntity: UserAddressDTO): UserAddressDTO

    fun delete(id: Long)
}