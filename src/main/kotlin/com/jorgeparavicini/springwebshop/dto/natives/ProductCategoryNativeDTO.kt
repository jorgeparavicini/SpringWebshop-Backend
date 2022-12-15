package com.jorgeparavicini.springwebshop.dto.natives

interface ProductCategoryNativeDTO {
    fun getId(): Long;
    fun getName(): String;
    fun getDescription(): String?;
    fun getIcon(): String?;
    fun getParentCategory(): Long?;
}