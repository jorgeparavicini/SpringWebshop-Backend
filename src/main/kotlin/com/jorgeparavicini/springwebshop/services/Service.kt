package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.BaseEntity
import com.jorgeparavicini.springwebshop.database.repositories.BaseRepository
import com.jorgeparavicini.springwebshop.dto.DTO
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import javax.transaction.Transactional

interface Service<TEntity : BaseEntity, TDto : DTO, TRepo : BaseRepository<TEntity>> {

    val repo: TRepo

    fun TEntity.toDto(): TDto

    fun TDto.toEntity(): TEntity

    fun getAll(): Iterable<TDto> {
        return repo.findAll().map { it.toDto() }
    }

    fun find(id: Long): TDto {
        return repo.findById(id).orElseThrow { NotFoundException("Could not find entity with id: $id") }.toDto()
    }

    fun create(newEntity: TDto): TDto {
        val entity = newEntity.toEntity()
        return repo.save(entity).toDto()
    }

    fun update(id: Long, newEntity: TDto): TDto {
        if (id != newEntity.id)
            throw BadRequestException("The passed id ($id) does not match the id of the entity: ${newEntity.id}")
        val entity = newEntity.toEntity()
        return repo.save(entity).toDto()
    }

    @Transactional
    fun delete(id: Long) {
        repo.softDelete(id)
    }
}