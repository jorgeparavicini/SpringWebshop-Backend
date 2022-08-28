package com.jorgeparavicini.springwebshop.database.entities

import javax.persistence.*

@Entity
class Person(
    @Column(nullable = false)
    val name: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null
)