package com.jorgeparavicini.springwebshop.models

data class RelatedProductDTO(
    override var id: Long,
    val productId: Long,
    val relatedProductId: Long,
    val relevance: Float
) : DTO()
