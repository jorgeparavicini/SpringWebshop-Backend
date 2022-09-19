package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.*

@Entity
class ProductCategory(
    val name: String,
    val description: String? = null,

    @OneToMany(mappedBy = "category")
    val products: List<Product>,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long? = null
): BaseEntity()