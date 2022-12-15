package com.jorgeparavicini.springwebshop.database.entities

import org.hibernate.annotations.Where
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@Where(clause = "deleted=false")
class RecommendedProduct(
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    id: Long? = null
) : BaseEntity(id = id)