package com.jorgeparavicini.springwebshop.database.entities

import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Where
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@Where(clause = "deleted=false")
class Product(
    val name: String,
    @Column(length = 5000)
    val description: String,
    val price: Float,
    @Column(columnDefinition = "FLOAT DEFAULT 0")
    val shippingPrice: Float,
    val oldPrice: Float?,
    val thumbnailUri: String,

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    val category: ProductCategory,

    @ManyToOne(optional = false)
    @JoinColumn(name = "vendor_id", nullable = false)
    val vendor: Vendor,

    id: Long? = null
) : BaseEntity(id = id)