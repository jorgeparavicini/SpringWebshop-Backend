package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.*

@Entity
class ProductCategory(
    val name: String,
    val description: String? = null,
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null
) {}