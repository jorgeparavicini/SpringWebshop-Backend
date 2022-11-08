package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Vendor(
    val name: String,
    @Column(length = 2500)
    val description: String,
    val street: String,
    val city: String,
    val postalCode: String,
    val country: String,
) : BaseEntity()