package com.jorgeparavicini.springwebshop.models

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class CreateReviewDTO(
    var productId: Long,

    @Min(value = 1)
    @Max(value = 5)
    var rating: Int,

    var comment: String?
)