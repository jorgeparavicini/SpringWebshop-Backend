package com.jorgeparavicini.springwebshop.database.entities

import org.hibernate.annotations.Where
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@Where(clause = "deleted=false")
class OrderItem(
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    val quantity: Int,
) : BaseEntity()