package com.memories.api.memories_api.core.common.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.memories.api.memories_api.core.common.dto.ErrorResponse;
import com.memories.api.memories_api.core.common.dto.ValidationErrorResponse;
import com.memories.api.memories_api.feature.auth.exception.AuthException;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(AuthException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ErrorResponse handleAuthException(
                        AuthException ex) {
                return new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                ex.getMessage(),
                                LocalDateTime.now());
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        @ResponseStatus(HttpStatus.CONFLICT)
        public ErrorResponse handleDataIntegrityViolationException(
                        DataIntegrityViolationException ex) {
                return new ErrorResponse(
                                HttpStatus.CONFLICT.value(),
                                "Email or username already exists",
                                LocalDateTime.now());
        }

        private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public ErrorResponse handleException(
                        Exception ex) {

                log.error("Unexpected error: ", ex);

                return new ErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Internal server error",
                                LocalDateTime.now());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ValidationErrorResponse handleValidationException(
                        MethodArgumentNotValidException ex) {

                Map<String, String> errors = new HashMap<>();

                ex.getBindingResult()
                                .getFieldErrors()
                                .forEach(error -> errors.put(
                                                error.getField(),
                                                error.getDefaultMessage()));

                return new ValidationErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                "Validation failed",
                                LocalDateTime.now(),
                                errors);
        }

}