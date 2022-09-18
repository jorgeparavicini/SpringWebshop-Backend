package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Vendor(
    val name: String,
    val description: String,
    val street: String,
    val city: String,
    val postalCode: String,
    val country: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long? = null
) : BaseEntity()