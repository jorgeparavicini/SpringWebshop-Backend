package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class OrderItem(
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    val quantity: Int,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long? = null
) : BaseEntity()