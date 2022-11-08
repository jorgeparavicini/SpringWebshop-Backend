package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.*

@Entity
@Table(name = "product_order")
class Order(
    @Column(length = 2500)
    val comments: String,
    val street: String,
    val city: String,
    val postalCode: String,
    val country: String,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "orderItems")
    val orderItems: Set<OrderItem>,

    val userId: String,

    id: Long? = null
) : BaseEntity(id = id)