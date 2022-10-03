package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.*

@Entity
class ProductCategory(
    val name: String,
    @Column(length = 2500)
    val description: String? = null,
    val icon: String? = null,

    @ManyToOne(optional = true)
    @JoinColumn(name = "parent_category_id", nullable = true)
    val parentCategory: ProductCategory?,

    @OneToMany(mappedBy = "parentCategory")
    val subCategories: List<ProductCategory>,

    @OneToMany(mappedBy = "category")
    val products: List<Product>,

    id: Long? = null
) : BaseEntity(id = id)