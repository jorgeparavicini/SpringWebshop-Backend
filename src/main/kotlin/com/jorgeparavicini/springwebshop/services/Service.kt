package com.jorgeparavicini.springwebshop.services

interface Service<T> {
    fun find(id: Long): T
    fun getAll(): Iterable<T>
    fun create(newEntity: T): T
    fun update(id: Long, newEntity: T): T
    fun delete(id: Long)
}