package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.dto.VendorDTO
import com.jorgeparavicini.springwebshop.services.VendorService
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["api/vendors"], produces = [MediaType.APPLICATION_JSON_VALUE])
class VendorController(private val service: VendorService) {
    @GetMapping
    fun getAll(): List<VendorDTO> {
        return service.getAll().toList()
    }

    @GetMapping("{id}")
    fun find(@PathVariable id: Long): VendorDTO {
        return service.find(id)
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create:vendor')")
    fun create(@Valid @RequestBody newVendor: VendorDTO): VendorDTO {
        return service.create(newVendor)
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('update:vendor')")
    fun update(@PathVariable id: Long, @Valid @RequestBody newVendor: VendorDTO): VendorDTO {
        return service.update(id, newVendor)
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('delete:vendor')")
    fun delete(@PathVariable id: Long) {
        service.delete(id)
    }
}