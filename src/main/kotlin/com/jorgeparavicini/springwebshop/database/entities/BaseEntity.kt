package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "deleted", columnDefinition = "Boolean default False")
    val deleted: Boolean = false
)