package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.RecommendedProduct
import com.jorgeparavicini.springwebshop.database.repositories.ProductRepository
import com.jorgeparavicini.springwebshop.database.repositories.RecommendedProductRepository
import com.jorgeparavicini.springwebshop.entities.ProductTest
import com.jorgeparavicini.springwebshop.entities.RecommendedProductTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class RecommendedProductsServiceTest {
    // Repos
    private val repo: RecommendedProductRepository = mockk()
    private val productRepo: ProductRepository = mockk()

    // SUT
    private val service = RecommendedProductsServiceImpl(repo, productRepo)


    @Test
    fun whenGetAll_thenReturnAll() {
        // Given
        every { repo.findAll() } returns RecommendedProductTest.recommendedProductList
        every { productRepo.getAverageRating(1) } returns 0.0.toFloat()
        every { productRepo.getNrOfRatings(1) } returns 0

        // When
        val result = service.getAll().toList()

        // Then
        verify(exactly = 1) { repo.findAll() }
        verify(exactly = 1) { productRepo.getAverageRating(1) }
        verify(exactly = 1) { productRepo.getNrOfRatings(1) }
    }

    @Test
    fun whenRegenerate_thenRegenerate() {
        // Given
        every { repo.deleteAll() } returns Unit
        every { productRepo.findRecommended(10) } returns ProductTest.productList
        every { repo.saveAll<RecommendedProduct>(any()) } returns RecommendedProductTest.recommendedProductList

        // When
        service.regenerate()

        // Then
        verify(exactly = 1) { repo.deleteAll() }
        verify(exactly = 1) { productRepo.findRecommended(10) }
        verify(exactly = 1) { repo.saveAll<RecommendedProduct>(any()) }
    }

}