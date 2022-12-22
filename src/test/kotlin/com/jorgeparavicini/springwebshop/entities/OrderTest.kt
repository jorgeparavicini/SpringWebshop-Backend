package com.jorgeparavicini.springwebshop.entities

import com.jorgeparavicini.springwebshop.database.entities.Order
import java.time.LocalDateTime
import java.time.Month

class OrderTest {
    companion object {
        val order1 = Order(
            "Comment 1",
            AddressTest.address1,
            emptySet(),
            "user1",
            LocalDateTime.of(2022, Month.NOVEMBER, 10, 10, 10, 10),
            1
        )

        val emptyOrderList = listOf<Order>()
        val orderList = listOf(order1)
    }
}