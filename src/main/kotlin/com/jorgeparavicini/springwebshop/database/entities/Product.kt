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

    @ManyToOne(optional = false)
    @JoinColumn(name = "vendor_id", nullable = false)
    val vendor: Vendor,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long? = null
) : BaseEntity()