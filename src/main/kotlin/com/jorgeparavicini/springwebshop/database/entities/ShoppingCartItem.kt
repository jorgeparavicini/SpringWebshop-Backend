package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.*

@Entity
class ShoppingCartItem(
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    val quantity: Int,

    val userId: String,

    id: Long? = null
): BaseEntity(id = id)
