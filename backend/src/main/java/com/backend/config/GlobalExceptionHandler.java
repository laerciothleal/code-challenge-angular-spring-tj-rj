package com.backend.config;

import com.backend.controller.v1.response.ApiErrorResponse;
import com.backend.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiErrorResponse handleResourceNotFound(ResourceNotFoundException e) {
        log.warn("Resource not found: {}", e.getMessage());
        return buildApiError(e.getMessage(), null, NOT_FOUND);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiErrorResponse handleNoHandlerFound(NoHandlerFoundException e) {
        log.warn("No handler found: {}", e.getMessage());
        return buildApiError(e.getMessage(), null, NOT_FOUND);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<String> validationErrors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format(
                        "Field error in object '%s' on field '%s'. Received value is '%s', %s",
                        error.getObjectName(),
                        error.getField(),
                        error.getRejectedValue(),
                        error.getDefaultMessage()))
                .toList();
        log.warn("Validation failed: {}", validationErrors);
        return buildApiError("Validation error", validationErrors, BAD_REQUEST);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({TypeMismatchException.class, MissingServletRequestParameterException.class, IllegalArgumentException.class})
    public ApiErrorResponse handleBadRequest(Exception e) {
        log.warn("Bad request: {}", e.getMessage());
        return buildApiError(e.getMessage(), null, BAD_REQUEST);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiErrorResponse handleDataIntegrityViolation(DataIntegrityViolationException e) {
        log.warn("Data integrity violation", e);
        return buildApiError(
                "Violação de integridade referencial",
                List.of("Não foi possível excluir o recurso porque ele está associado a outro registro."),
                BAD_REQUEST);
    }

    @ResponseStatus(METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiErrorResponse handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.warn("HTTP method not supported: {}", e.getMessage());
        return buildApiError(e.getMessage(), null, METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ApiErrorResponse handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
        log.warn("HTTP media type not supported: {}", e.getMessage());
        return buildApiError(e.getMessage(), null, UNSUPPORTED_MEDIA_TYPE);
    }

    @ResponseStatus(NOT_ACCEPTABLE)
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ApiErrorResponse handleMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException e) {
        log.warn("HTTP media type not acceptable: {}", e.getMessage());
        return buildApiError(e.getMessage(), null, NOT_ACCEPTABLE);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiErrorResponse handleUnexpected(Exception e) {
        log.error("Unhandled exception", e);
        return buildApiError("Erro interno no servidor", null, INTERNAL_SERVER_ERROR);
    }

    private ApiErrorResponse buildApiError(String message, Collection<String> validationErrors, HttpStatus httpStatus) {
        return new ApiErrorResponse(
                httpStatus.value(),
                LocalDateTime.now(),
                httpStatus.getReasonPhrase(),
                message,
                validationErrors
        );
    }
}
