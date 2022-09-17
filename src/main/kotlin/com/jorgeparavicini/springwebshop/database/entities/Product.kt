package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.*

@Entity
class Product(
    val name: String,
    val description: String,
    val price: Float,

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    val category: ProductCategory,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {}