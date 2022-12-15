package com.jorgeparavicini.springwebshop.tasks

import com.jorgeparavicini.springwebshop.services.RecommendedProductsService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class UpdateRecommendedProducts(private val service: RecommendedProductsService) {
    companion object {
        private val logger = LoggerFactory.getLogger(UpdateRecommendedProducts.Companion::class.java)
    }

    @Scheduled(fixedRate = 5000)
    fun update() {
        service.regenerate()
        logger.info("Updated recommended products");
    }
}