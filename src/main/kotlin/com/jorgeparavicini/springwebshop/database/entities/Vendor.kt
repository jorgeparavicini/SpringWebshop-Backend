package com.jorgeparavicini.springwebshop.database.entities

import org.hibernate.annotations.Where
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@Where(clause = "deleted=false")
class Vendor(
    val name: String,
    @Column(length = 2500)
    val description: String,

    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    val address: Address,
    id: Long? = null
) : BaseEntity(id = id)