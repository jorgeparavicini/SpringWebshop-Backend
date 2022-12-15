package com.jorgeparavicini.springwebshop.dto

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class CreateReviewDTO(
    var productId: Long,

    @field:Min(0)
    @field:Max(5)
    var rating: Int,

    var comment: String?
)