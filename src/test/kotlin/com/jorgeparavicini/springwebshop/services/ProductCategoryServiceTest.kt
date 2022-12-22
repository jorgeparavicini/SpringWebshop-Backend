package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.repositories.ProductCategoryRepository
import com.jorgeparavicini.springwebshop.dto.ProductCategoryDTO
import com.jorgeparavicini.springwebshop.dto.ProductCategoryDTOTest
import com.jorgeparavicini.springwebshop.entities.ProductCategoryTest
import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductCategoryServiceTest {
    // Repos
    private val repo: ProductCategoryRepository = mockk()

    // SUT
    private val service = ProductCategoryServiceImpl(repo)

    @Test
    fun whenGetAll_thenReturnAll() {
        // Given
        every { repo.findAll() } returns ProductCategoryTest.productCategoryList

        // When
        val result = service.getAll().toList()

        // Then
        verify(exactly = 1) { repo.findAll() }
        assertTrue(ProductCategoryDTOTest.productCategoryList.containsAll(result))
    }

    @Test
    fun givenExistingCategory_whenFind_thenReturnCategory() {
        // Given
        every { repo.findById(1) } returns Optional.of(ProductCategoryTest.productCategory1)

        // When
        val result = service.find(1)

        // Then
        verify(exactly = 1) { repo.findById(1) }
        assertEquals(ProductCategoryDTOTest.productCategory1, result)
    }

    @Test
    fun givenNoCategory_whenFind_thenThrowNotFound() {
        // Given
        every { repo.findById(1) } returns Optional.empty()

        // When
        val result = assertThrows<NotFoundException> { service.find(1) }

        // Then
        verify(exactly = 1) { repo.findById(1) }
        assertEquals("Could not find entity with id: 1", result.message)
    }

    @Test
    fun givenCategory_whenCreate_thenCreateNewCategory() {
        // Given
        every { repo.save(any()) } returns ProductCategoryTest.productCategory1

        // When
        val result = service.create(ProductCategoryDTOTest.productCategory1)

        // Then
        verify(exactly = 1) { repo.save(any()) }
        assertEquals(ProductCategoryDTOTest.productCategory1, result)
    }

    @Test
    fun givenCategory_whenUpdate_thenUpdateCategory() {
        // Given
        every { repo.save(any()) } returns ProductCategoryTest.productCategory1

        // When
        val result = service.update(1, ProductCategoryDTOTest.productCategory1)

        // Then
        verify(exactly = 1) { repo.save(any()) }
        assertEquals(ProductCategoryDTOTest.productCategory1, result)
    }

    @Test
    fun givenInvalidCategory_whenUpdate_thenThrowBadRequest() {
        // When
        val result = assertThrows<BadRequestException> { service.update(2, ProductCategoryDTOTest.productCategory1) }
        // Then
        assertEquals("The passed id (2) does not match the id of the entity: 1", result.message)
    }

    @Test
    fun whenDelete_thenDeleteCategory() {
        // Given
        every { repo.softDelete(1) } returns Unit

        // When
        service.delete(1)

        // Then
        verify(exactly = 1) { repo.softDelete(1) }
    }
}