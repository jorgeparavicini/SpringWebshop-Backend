package com.jorgeparavicini.springwebshop.database.entities

import org.hibernate.annotations.Where
import javax.persistence.Entity

@Entity
@Where(clause = "deleted=false")
class Address(
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val country: String,

    id: Long? = null
) : BaseEntity(id = id)