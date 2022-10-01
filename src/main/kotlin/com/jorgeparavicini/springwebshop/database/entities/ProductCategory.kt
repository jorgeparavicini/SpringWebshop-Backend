package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.*

@Entity
class ProductCategory(
    val name: String,
    val description: String? = null,

    @ManyToOne(optional = true)
    @JoinColumn(name = "parent_category_id", nullable = true)
    val parentCategory: ProductCategory?,

    @OneToMany(mappedBy = "parentCategory")
    val subCategories: List<ProductCategory>,

    @OneToMany(mappedBy = "category")
    val products: List<Product>,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long? = null
): BaseEntity()