package com.jorgeparavicini.springwebshop

import com.jorgeparavicini.springwebshop.database.entities.ProductCategory
import com.jorgeparavicini.springwebshop.database.repositories.ProductCategoryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpringWebShopApplication {
    private val log: Logger = LoggerFactory.getLogger(SpringWebShopApplication::class.java)

    @Bean
    fun seedProductCategory(repo: ProductCategoryRepository): CommandLineRunner {
        return CommandLineRunner {_ ->
            repo.save(ProductCategory("Test", "Test"))

            for (category in repo.findAll()) {
                log.info("Category: ${category.id}")
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringWebShopApplication>(*args)
}
