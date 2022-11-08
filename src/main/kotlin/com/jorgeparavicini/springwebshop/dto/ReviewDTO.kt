package com.jorgeparavicini.springwebshop.dto

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class ReviewDTO(
    override var id: Long,
    var productId: Long,
    var userId: String,

    @Min(value = 1)
    @Max(value = 5)
    var rating: Int,

    var comment: String?
) : DTO()