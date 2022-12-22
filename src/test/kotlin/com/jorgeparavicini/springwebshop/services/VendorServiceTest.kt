package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.repositories.AddressRepository
import com.jorgeparavicini.springwebshop.database.repositories.VendorRepository
import com.jorgeparavicini.springwebshop.dto.VendorDTOTest
import com.jorgeparavicini.springwebshop.entities.AddressTest
import com.jorgeparavicini.springwebshop.entities.VendorTest
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

class VendorServiceTest {
    // Repos
    private val repo: VendorRepository = mockk()
    private val addressRepo: AddressRepository = mockk()

    // SUT
    private val service = VendorServiceImpl(repo, addressRepo)


    @Test
    fun whenGetAll_thenReturnAll() {
        // Given
        every { repo.findAll() } returns VendorTest.vendorList

        // When
        val result = service.getAll().toList()

        // Then
        verify(exactly = 1) { repo.findAll() }
        assertTrue(VendorDTOTest.vendorList.containsAll(result))
    }

    @Test
    fun givenExistingVendor_whenFind_thenReturnVendor() {
        // Given
        every { repo.findById(1) } returns Optional.of(VendorTest.vendor1)

        // When
        val result = service.find(1)

        // Then
        verify(exactly = 1) { repo.findById(1) }
        assertEquals(VendorDTOTest.vendor1, result)
    }

    @Test
    fun givenNoVendor_whenFind_thenThrowNotFound() {
        // Given
        every { repo.findById(1) } returns Optional.empty()

        // When
        val result = assertThrows<NotFoundException> { service.find(1) }

        // Then
        verify(exactly = 1) { repo.findById(1) }
        assertEquals("Could not find entity with id: 1", result.message)
    }

    @Test
    fun givenVendor_whenCreate_thenCreateNewVendor() {
        // Given
        every { repo.save(any()) } returns VendorTest.vendor1
        every { addressRepo.findById(1) } returns Optional.of(AddressTest.address1)

        // When
        val result = service.create(VendorDTOTest.vendor1)

        // Then
        verify(exactly = 1) { repo.save(any()) }
        assertEquals(VendorDTOTest.vendor1, result)
    }

    @Test
    fun givenVendor_whenUpdate_thenUpdateVendor() {
        // Given
        every { repo.findById(1) } returns Optional.of(VendorTest.vendor1)
        every { repo.save(any()) } returns VendorTest.vendor1
        every { addressRepo.findById(1) } returns Optional.of(AddressTest.address1)

        // When
        val result = service.update(1, VendorDTOTest.vendor1)

        // Then
        verify(exactly = 1) { repo.save(any()) }
        assertEquals(VendorDTOTest.vendor1, result)
    }

    @Test
    fun givenInvalidAddress_whenUpdate_thenThrowBadRequest() {
        // Given
        every { repo.findById(2) } returns Optional.empty()

        // When
        val result =
            assertThrows<BadRequestException> { service.update(2, VendorDTOTest.vendor1) }

        // Then
        assertEquals("The passed id (2) does not match the id of the entity: 1", result.message)
    }

    @Test
    fun whenDelete_thenDeleteVendor() {
        // Given
        every { repo.softDelete(1) } returns Unit

        // When
        service.delete(1)

        // Then
        verify(exactly = 1) { repo.softDelete(1) }
    }
}