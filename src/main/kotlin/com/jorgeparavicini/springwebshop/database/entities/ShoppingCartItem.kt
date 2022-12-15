package com.jorgeparavicini.springwebshop.database.entities

import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Where(clause = "deleted=false")
class ShoppingCartItem(
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    val quantity: Int,

    val userId: String,

    id: Long? = null
) : BaseEntity(id = id)
