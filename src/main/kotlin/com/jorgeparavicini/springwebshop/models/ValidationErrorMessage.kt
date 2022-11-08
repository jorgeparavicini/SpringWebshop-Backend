package com.jorgeparavicini.springwebshop.models

import org.springframework.validation.FieldError

data class ValidationErrorMessage(val message: String, val fieldErrors: List<FieldError>)
