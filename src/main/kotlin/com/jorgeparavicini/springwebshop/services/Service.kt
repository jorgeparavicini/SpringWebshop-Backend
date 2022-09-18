package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.BaseEntity
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.models.DTO
import org.springframework.data.repository.CrudRepository
import org.webjars.NotFoundException

interface Service<TEntity : BaseEntity, TDto : DTO, TRepo : CrudRepository<TEntity, Long>> {

    val repo: TRepo

    fun TEntity.toTDto(): TDto

    fun TDto.toEntity(): TEntity

    fun getAll(): Iterable<TDto> {
        return repo.findAll().map { it.toTDto() }
    }

    fun find(id: Long): TDto {
        return repo.findById(id).orElseThrow { NotFoundException("Could not find entity with id: $id") }.toTDto()
    }

    fun create(newEntity: TDto): TDto {
        newEntity.id = 0
        val entity = newEntity.toEntity()
        return repo.save(entity).toTDto()
    }

    fun update(id: Long, newEntity: TDto): TDto {
        if (id != newEntity.id)
            throw BadRequestException("The passed id ($id) does not match the id of the entity: ${newEntity.id}")
        val entity = newEntity.toEntity()
        return repo.save(entity).toTDto()
    }

    fun delete(id: Long) {
        repo.deleteById(id)
    }
}