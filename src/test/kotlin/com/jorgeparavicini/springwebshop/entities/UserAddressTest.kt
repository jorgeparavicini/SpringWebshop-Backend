package com.jorgeparavicini.springwebshop.entities

import com.jorgeparavicini.springwebshop.database.entities.UserAddress

class UserAddressTest {
    companion object {
        val userAddress1 = UserAddress("name1", "user1", AddressTest.address1, 1)

        val userAddressList = listOf(userAddress1)
    }
}