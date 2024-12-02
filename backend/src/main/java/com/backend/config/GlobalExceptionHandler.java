package com.backend.config;

import com.backend.controller.response.ApiErrorResponse;
import com.backend.exception.ObjectNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({ServerWebInputException.class, TypeMismatchException.class})
    @ResponseBody
    public ApiErrorResponse handleServerWebInputException(ServerWebInputException e) {
        if (e.getCause() instanceof TypeMismatchException) {
            var error = (TypeMismatchException) e.getCause();
            String validationErrors;
            if (error.getPropertyName() == null) {
                validationErrors = String.format(
                        "Field type error. Received value is '%s', required type is '%s'",
                        error.getValue(),
                        error.getRequiredType());
            } else {
                validationErrors = String.format(
                        "Field error on field '%s'. Received value is '%s', required type is '%s'",
                        error.getPropertyName(),
                        error.getValue(),
                        error.getRequiredType());
            }
            return buildApiError("Validation error", List.of(validationErrors), BAD_REQUEST);
        }
        return buildApiError(e, BAD_REQUEST);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseBody
    public ApiErrorResponse handleWebExchangeBindException(WebExchangeBindException e) {
        log.error("BadRequest exception", e);
        var validationErrors = e.getFieldErrors().stream().map(error ->
                String.format("Field error in object '%s' on field '%s'. Received value is '%s', %s",
                        error.getObjectName(),
                        error.getField(),
                        error.getRejectedValue(),
                        error.getDefaultMessage())
        ).collect(Collectors.toList());

        return buildApiError("Validation error", validationErrors, BAD_REQUEST);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Method argument not valid exception", e);
        var validationErrors = e.getBindingResult().getFieldErrors().stream().map(error ->
                String.format("Field error in object '%s' on field '%s'. Received value is '%s', %s",
                        error.getObjectName(),
                        error.getField(),
                        error.getRejectedValue(),
                        error.getDefaultMessage())
        ).collect(Collectors.toList());

        return buildApiError("Validation error", validationErrors, BAD_REQUEST);
    }

    @ResponseStatus(METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ApiErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HTTP request method not supported", e);
        return buildApiError(e, METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ApiErrorResponse handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("HTTP media type not supported", e);
        return buildApiError(e, UNSUPPORTED_MEDIA_TYPE);
    }

    @ResponseStatus(NOT_ACCEPTABLE)
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseBody
    public ApiErrorResponse handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
        log.error("HTTP media type not acceptable", e);
        return buildApiError(e, NOT_ACCEPTABLE);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ApiErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("Missing request parameter", e);
        return buildApiError(e, BAD_REQUEST);
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ApiErrorResponse handleAccessDeniedException(AccessDeniedException e) {
        log.error("Access denied", e);
        return buildApiError(e, FORBIDDEN);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ApiErrorResponse handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("No handler found", e);
        return buildApiError(e, NOT_FOUND);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ApiErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("Entity not found", e);
        return buildApiError(e, NOT_FOUND);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseBody
    public ApiErrorResponse handleEntityObjectNotFoundException(ObjectNotFoundException e) {
        log.error("Entity not found", e);
        return buildApiError(e, NOT_FOUND);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ApiErrorResponse handleGenericRepositoryException(RuntimeException e) {
        log.error("Internal exception", e);
        return buildApiInternalError(new RuntimeException(e), INTERNAL_SERVER_ERROR);
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

    private ApiErrorResponse buildApiError(Exception e, HttpStatus httpStatus) {
        return new ApiErrorResponse(
                httpStatus.value(),
                LocalDateTime.now(),
                httpStatus.getReasonPhrase(),
                e.getMessage(),
                null
        );
    }

    private ApiErrorResponse buildApiInternalError(Exception e, HttpStatus httpStatus) {
        return new ApiErrorResponse(
                httpStatus.value(),
                LocalDateTime.now(),
                httpStatus.getReasonPhrase(),
                e.getMessage(),
                null
        );
    }
}
