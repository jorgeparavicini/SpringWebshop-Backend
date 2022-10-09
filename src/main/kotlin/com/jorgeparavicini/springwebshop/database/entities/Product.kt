package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Product(
    val name: String,
    @Column(length = 2500)
    val description: String,
    val price: Float,
    val thumbnailUri: String,

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    val category: ProductCategory,

    @ManyToOne(optional = false)
    @JoinColumn(name = "vendor_id", nullable = false)
    val vendor: Vendor,

    id: Long? = null
) : BaseEntity(id = id)