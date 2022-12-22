package com.jorgeparavicini.springwebshop.dto

class UserAddressDTOTest {
    companion object {
        val userAddress1 = UserAddressDTO(1, "name1", AddressDTOTest.address1)

        val userAddressList = listOf(userAddress1)
    }
}