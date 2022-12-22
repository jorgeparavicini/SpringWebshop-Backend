package com.jorgeparavicini.springwebshop.entities

import com.jorgeparavicini.springwebshop.database.entities.Vendor

class VendorTest {
    companion object {
        val vendor1 = Vendor("name1", "description1", AddressTest.address1, 1)

        val vendorList = listOf(vendor1)
    }
}