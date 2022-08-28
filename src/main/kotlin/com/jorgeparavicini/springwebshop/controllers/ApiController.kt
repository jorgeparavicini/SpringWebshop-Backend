package com.jorgeparavicini.springwebshop.controllers

import com.jorgeparavicini.springwebshop.Message
import com.jorgeparavicini.springwebshop.database.entities.Customer
import com.jorgeparavicini.springwebshop.database.repositories.CustomerRepository
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ApiController(private val repo: CustomerRepository) {

    @GetMapping(value = ["/public"])
    fun publicEndpoint(): List<Customer> {
        return repo.findAll().toList()
    }

    @GetMapping(value = ["/user"])
    fun privateEndpoint(): Message {
        return Message("All good. You DO NOT need to be authenticated.")
    }

    @GetMapping(value = ["/user/private-scoped"])
    @PreAuthorize("hasAuthority('read:test')")
    fun privateScopedEndpoint(): Message {
        return Message("All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope")
    }
}