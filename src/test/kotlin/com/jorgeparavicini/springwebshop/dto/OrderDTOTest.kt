package com.jorgeparavicini.springwebshop.dto

import java.time.LocalDateTime
import java.time.Month

class OrderDTOTest {
    companion object {
        val order1 = OrderDTO(
            1,
            "Comment 1",
            AddressDTOTest.address1.id,
            emptySet(),
            LocalDateTime.of(2022, Month.NOVEMBER, 10, 10, 10, 10),
            0.0
        )

        val orderList = listOf(order1)
    }
}