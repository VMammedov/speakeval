package org.project.speakeval.exception;

import lombok.extern.slf4j.Slf4j;
import org.project.speakeval.constants.Constants;
import org.project.speakeval.exception.utils.ExceptionCodes;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends DefaultErrorAttributes {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException exception,
                                                                     WebRequest request) {
        String message = exception.getMessage();
        log.error("Illegal argument {}", message, exception);
        return ofType(request, HttpStatus.BAD_REQUEST, message,
                ExceptionCodes.ILLEGAL_ARGUMENT_EXCEPTION.getExceptionKey());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException exception,
                                                                WebRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fe : exception.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fe.getField(), fe.getDefaultMessage());
        }
        return ofType(request,
                HttpStatus.BAD_REQUEST,
                ExceptionCodes.ARGUMENT_VALIDATION_EXCEPTION.getExceptionMessage(),
                ExceptionCodes.ARGUMENT_VALIDATION_EXCEPTION.getExceptionKey(),
                fieldErrors);
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<Map<String, Object>> handleAuthErrors(Exception exception,
                                                                WebRequest request) {
        String message = exception.getMessage();
        log.error("Unauthorized action attempt {}", message, exception);
        return ofType(request, HttpStatus.UNAUTHORIZED, message,
                ExceptionCodes.ENTITY_NOT_FOUND_EXCEPTION.getExceptionKey());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException exception,
                                                                  WebRequest request) {
        String message = exception.getMessage();
        log.error("Access denied {}", message, exception);
        return ofType(request, HttpStatus.FORBIDDEN, message,
                ExceptionCodes.ACCESS_DENIED_EXCEPTION.getExceptionKey());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception exception,
                                                         WebRequest request) {
        String message = exception.getMessage();
        log.error("Unknown exception {}", message, exception);
        return ofType(request, HttpStatus.INTERNAL_SERVER_ERROR, message,
                ExceptionCodes.UNKNOWN_EXCEPTION.getExceptionKey());
    }

    protected ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message,
                                                         String exceptionKey) {
        return ofType(request, status, message, exceptionKey, Collections.emptyMap());
    }

    private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message,
                                                       String exceptionKey, Map<String, String> validationErrors) {
        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put(Constants.HttpResponseConstants.STATUS, status.value());
        attributes.put(Constants.HttpResponseConstants.ERROR, status.getReasonPhrase());
        attributes.put(Constants.HttpResponseConstants.MESSAGE, message);
        attributes.put(Constants.HttpResponseConstants.ERRORS, validationErrors);
        attributes.put(Constants.HttpResponseConstants.KEY, exceptionKey);
        attributes.put(Constants.HttpResponseConstants.PATH, ((ServletWebRequest) request).getRequest().getRequestURI());
        attributes.put(Constants.HttpResponseConstants.TIMESTAMP, LocalDateTime.now());
        return new ResponseEntity<>(attributes, status);
    }
}
