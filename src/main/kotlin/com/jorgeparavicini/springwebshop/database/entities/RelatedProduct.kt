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
class RelatedProduct(
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    @ManyToOne(optional = false)
    @JoinColumn(name = "related_id", nullable = false, referencedColumnName = "id")
    val relatedProduct: Product,

    val relevance: Float,

    id: Long? = null
): BaseEntity(id = id)