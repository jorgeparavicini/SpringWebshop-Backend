package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.*

@Entity
class ShoppingCartItem(
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    val quantity: Int,

    val userId: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long? = null
): BaseEntity()
