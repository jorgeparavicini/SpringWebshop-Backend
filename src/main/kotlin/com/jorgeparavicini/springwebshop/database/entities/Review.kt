package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.*
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

    val comment: String?,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long? = null
) : BaseEntity()