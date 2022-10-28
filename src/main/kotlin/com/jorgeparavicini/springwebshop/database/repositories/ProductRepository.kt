package com.jorgeparavicini.springwebshop.database.repositories

import com.jorgeparavicini.springwebshop.database.entities.Product
import com.jorgeparavicini.springwebshop.database.entities.ProductCategory
import com.jorgeparavicini.springwebshop.database.entities.Vendor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*
import javax.persistence.Tuple

interface ProductRepository : BaseRepository<Product> {

    @Query(value = "SELECT AVG(CAST(r.rating AS float)) FROM Review r WHERE r.product.id = :#{#id}")
    fun getAverageRating(@Param("id") id: Long): Float?

    @Query(
        value = "SELECT p.* FROM product_category p WHERE id IN " +
                "(WITH RECURSIVE descendants(id, parent_category_id, name) AS (" +
                "SELECT category.id, parent_category_id, category.name " +
                "FROM product_category category " +
                "WHERE category.id = :#{#category} " +
                "UNION ALL " +
                "SELECT child.id, child.parent_category_id, child.name " +
                "FROM product_category child " +
                "JOIN descendants parent " +
                "ON child.parent_category_id = parent.id) " +
                "SELECT DISTINCT(id) as ids " +
                "FROM descendants)",
        nativeQuery = true
    )
    fun getSubcategories(@Param("category") category: Long): List<Long>

    @Query(
        value = "SELECT vendor.id FROM product " +
                "JOIN vendor ON product.vendor_id = vendor.id " +
                "WHERE category_id IN (" +
                "WITH RECURSIVE descendants(id, parent_category_id, name)" +
                "                   AS (SELECT category.id, parent_category_id, category.name" +
                "                       FROM product_category category" +
                "                       WHERE category.id IN (1, 3, 4)" +
                "                       UNION ALL" +
                "                       SELECT child.id, child.parent_category_id, child.name" +
                "                       FROM product_category child" +
                "                                JOIN descendants parent" +
                "                                     ON child.parent_category_id = parent.id)" +
                "SELECT DISTINCT(id) AS ids " +
                "FROM descendants) ",
        nativeQuery = true
    )
    fun getVendors(@Param("category") category: Long): List<Long>

    @Query(
        value = "SELECT  MAX(shipping_price) AS maxShippingCost, MIN(price) AS minPrice, MAX(price) AS maxPrice FROM product " +
                "WHERE category_id IN (" +
                "WITH RECURSIVE descendants(id, parent_category_id, name)" +
                "                   AS (SELECT category.id, parent_category_id, category.name" +
                "                       FROM product_category category" +
                "                       WHERE category.id IN (1, 3, 4)" +
                "                       UNION ALL" +
                "                       SELECT child.id, child.parent_category_id, child.name" +
                "                       FROM product_category child" +
                "                                JOIN descendants parent" +
                "                                     ON child.parent_category_id = parent.id)" +
                "SELECT DISTINCT(id) AS ids " +
                "FROM descendants)",
        nativeQuery = true
    )
    fun getSimpleProductFilterInfo(@Param("category") category: Long): Tuple

    @Query(
        value = "SELECT p FROM Product p WHERE " +
                "p.category.id IN :#{#subCategories} " +
                "AND (:#{#categories} IS NULL OR p.category.id IN :#{#categories}) " +
                "AND (:#{#vendors} IS NULL OR p.vendor.id IN :#{#vendors})" +
                "AND (:#{#minPrice} IS NULL OR p.price >= :#{#minPrice})" +
                "AND (:#{#maxPrice} IS NULL OR p.price <= :#{#maxPrice})" +
                "AND (:#{#maxShippingPrice} IS NULL OR p.shippingPrice <= :#{#maxShippingPrice})" +
                "AND (:#{#minRating} IS NULL OR (SELECT AVG(CAST(r.rating AS float)) " +
                "FROM Review r WHERE r.product.id = p.id) >= :#{#minRating})",
    )
    fun findAllBy(
        pageable: Pageable,
        @Param("subCategories") subCategories: List<Long>,
        @Param("categories") categories: List<Long>?,
        @Param("vendors") vendors: List<Long>?,
        @Param("minPrice") minPrice: Float?,
        @Param("maxPrice") maxPrice: Float?,
        @Param("maxShippingPrice") maxShippingPrice: Float?,
        @Param("minRating") minRating: Int?
    ): Page<Product>

}