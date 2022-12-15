package com.jorgeparavicini.springwebshop.database.entities

import org.hibernate.annotations.Where
import javax.management.monitor.StringMonitor
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@Where(clause = "deleted=false")
class UserAddress(
    val name: String,
    val userId: String,

    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    val address: Address,
    id: Long? = null
) : BaseEntity(id = id)