package com.jorgeparavicini.springwebshop.exceptions

class BadRequestException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {}