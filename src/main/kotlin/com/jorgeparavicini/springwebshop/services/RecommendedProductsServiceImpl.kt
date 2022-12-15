package com.jorgeparavicini.springwebshop.services

import com.jorgeparavicini.springwebshop.database.entities.Product
import com.jorgeparavicini.springwebshop.database.entities.RecommendedProduct
import com.jorgeparavicini.springwebshop.database.repositories.ProductRepository
import com.jorgeparavicini.springwebshop.database.repositories.RecommendedProductRepository
import com.jorgeparavicini.springwebshop.dto.ProductDTO
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class RecommendedProductsServiceImpl(
    private val repo: RecommendedProductRepository,
    private val productRepo: ProductRepository
) : RecommendedProductsService {

    companion object {
        const val RecommendedProductCount = 10;
    }

    private fun Product.toDto() =
        ProductDTO(
            id!!,
            name,
            description,
            shippingPrice,
            oldPrice,
            thumbnailUri,
            price,
            category.id!!,
            vendor.id!!,
            productRepo.getAverageRating(id),
            productRepo.getNrOfRatings(id)
        )

    override fun getAll(): Iterable<ProductDTO> {
        return repo.findAll().map { it.product.toDto() }
    }

    @Transactional
    override fun regenerate() {
        repo.deleteAll()
        val uniqueProducts = productRepo.findRecommended(RecommendedProductCount);
        repo.saveAll(uniqueProducts.map { RecommendedProduct(it) })
    }
}