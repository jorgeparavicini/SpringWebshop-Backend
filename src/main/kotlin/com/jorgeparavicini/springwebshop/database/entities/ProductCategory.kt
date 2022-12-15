package com.jorgeparavicini.springwebshop.database.entities

import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Where(clause = "deleted=false")
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