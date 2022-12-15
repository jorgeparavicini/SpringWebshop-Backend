package com.jorgeparavicini.springwebshop.dto.natives

interface VendorNativeDTO {
    fun getId(): Long;
    fun getName(): String;
    fun getDescription(): String;
    fun getAddressId(): Long;
}