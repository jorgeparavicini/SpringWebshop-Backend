package com.jorgeparavicini.springwebshop.config

import com.jorgeparavicini.springwebshop.exceptions.BadRequestException
import com.jorgeparavicini.springwebshop.exceptions.ForbiddenException
import com.jorgeparavicini.springwebshop.exceptions.NotFoundException
import com.jorgeparavicini.springwebshop.exceptions.UnauthorizedException
import com.jorgeparavicini.springwebshop.models.ValidationErrorMessage
import org.springdoc.api.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    fun handleBadRequest(request: HttpServletRequest, error: BadRequestException): ErrorMessage {
        return ErrorMessage(error.message ?: "Bad Request")
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    fun handleBadRequest(request: HttpServletRequest, error: MethodArgumentNotValidException): ValidationErrorMessage {
        val fieldErrors =
            error.bindingResult.fieldErrors.map { FieldError(it.objectName, it.field, it.defaultMessage ?: "") }
        return ValidationErrorMessage("validation error", fieldErrors)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    fun handleBadRequest(request: HttpServletRequest, error: MissingServletRequestParameterException): ErrorMessage {
        return ErrorMessage(error.message)
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    fun handleUnauthorized(request: HttpServletRequest, error: UnauthorizedException): ErrorMessage {
        return ErrorMessage(error.message ?: "Unauthorized")
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler
    fun handleAccessDenied(request: HttpServletRequest, error: AccessDeniedException): ErrorMessage {
        return ErrorMessage("Permission denied")
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler
    fun handleAccessDenied(request: HttpServletRequest, error: ForbiddenException): ErrorMessage {
        return ErrorMessage(error.message ?: "Permission denied")
    }

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

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    fun handleInternalError(request: HttpServletRequest, error: Throwable): ErrorMessage {
        return ErrorMessage(error.message)
    }
}