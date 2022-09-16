package com.jorgeparavicini.springwebshop.config

import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import org.springdoc.api.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    fun handleNotFound(request: HttpServletRequest, error: NoHandlerFoundException): ErrorMessage {
        return ErrorMessage("Not found")
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    fun handleNotFound(request: HttpServletRequest, error: NotFoundException): ErrorMessage {
        return ErrorMessage(error.message ?: "Not Found")
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler
    fun handleAccessDenied(request: HttpServletRequest, error: AccessDeniedException): ErrorMessage {
        return ErrorMessage("Permission denied")
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    fun handleInternalError(request: HttpServletRequest, error: Throwable): ErrorMessage {
        return ErrorMessage(error.message)
    }
}