package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Entity
class Review(
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    val userId: String,

    @Min(value = 1)
    @Max(value = 5)
    val rating: Int,

    @Column(length = 2500)
    val comment: String?,

    id: Long? = null
) : BaseEntity(id = id)