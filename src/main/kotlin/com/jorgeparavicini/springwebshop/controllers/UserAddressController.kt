package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.dto.UserAddressDTO
import com.jorgeparavicini.springwebshop.services.UserAddressService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/api/user-address"], produces = [MediaType.APPLICATION_JSON_VALUE])
class UserAddressController(private val service: UserAddressService) {

    @GetMapping
    fun getAll(): List<UserAddressDTO> {
        return service.getAll().toList()
    }

    @GetMapping("{id}")
    fun find(@PathVariable id: Long): UserAddressDTO {
        return service.find(id)
    }

    @PostMapping
    fun create(@Valid @RequestBody newAddress: UserAddressDTO): UserAddressDTO {
        return service.create(newAddress)
    }

    @PutMapping("{id}")
    fun update(@Valid @RequestBody existingAddress: UserAddressDTO, @PathVariable id: Long): UserAddressDTO {
        return service.update(id, existingAddress)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) {
        service.delete(id)
    }
}