package com.jorgeparavicini.springwebshop.database.repositories

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseRepository<TEntity>: CrudRepository<TEntity, Long> {

    @Query("select e from #{#entityName} e where e.deleted = false")
    override fun findAll(): MutableIterable<TEntity>

    @Query("select e from #{#entityName} e where e.deleted = true")
    fun recycleBin(): MutableIterable<TEntity>

    @Query("update #{#entityName} e set e.deleted = true where e.id=?1")
    @Modifying
    fun softDelete(id: Long)
}