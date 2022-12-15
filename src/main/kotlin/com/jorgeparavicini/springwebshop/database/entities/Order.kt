package com.jorgeparavicini.springwebshop.database.entities

import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "product_order")
@Where(clause = "deleted=false")
class Order(
    @Column(length = 2500)
    val comments: String,

    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    val address: Address,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "orderItems")
    val orderItems: Set<OrderItem>,

    val userId: String,
    val date: LocalDateTime,

    id: Long? = null
) : BaseEntity(id = id)