package com.spring.libarary_management_system.Exception;

import java.nio.file.AccessDeniedException;

import com.spring.libarary_management_system.DTOs.BasicResponse;
import org.springframework.security.core.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // === Common Utility === //
    private ResponseEntity<BasicResponse> buildErrorResponse(Exception ex, WebRequest request, HttpStatus status) {
        BasicResponse response = new BasicResponse(  ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, status);
    }

    private ResponseEntity<BasicResponse> buildErrorResponse(String message, WebRequest request, HttpStatus status) {
        BasicResponse response = new BasicResponse(  message, request.getDescription(false));
        return new ResponseEntity<>( response, status);
    }

    @ExceptionHandler(MailSendingException.class)
    public ResponseEntity<BasicResponse> handleMailException(MailSendingException ex, HttpServletRequest request) {

        BasicResponse response = new BasicResponse( ex.getMessage(),request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // === Authentication & Security Exceptions === //

    @ExceptionHandler({
            AuthenticationCredentialsNotFoundException.class,
            BadCredentialsException.class,
            AuthenticationException.class
    })
    public ResponseEntity<BasicResponse> handleAuthenticationExceptions(Exception ex, WebRequest request) {
        String message = (ex instanceof BadCredentialsException) ? Messages.BAD_CREDENTIALS :
                (ex instanceof AuthenticationException) ? Messages.AUTH_FAILED :
                        ex.getMessage();
        return buildErrorResponse(message, request, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BasicResponse> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        return buildErrorResponse(Messages.ACCESS_DENIED, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<BasicResponse> handleJwtExpired(ExpiredJwtException ex, WebRequest request) {
        return buildErrorResponse(Messages.SESSION_EXPIRED, request, HttpStatus.BAD_REQUEST);
    }


    // === Business Exceptions === //

    @ExceptionHandler({
            InvalidTokenException.class,
            InvalidResetCodeException.class,
            InvalidCurrentPasswordException.class,
            UserNotFoundException.class,
            CategoryNotFoundException.class,
            EmailAlreadyExistsException.class,
            RoleNotFoundException.class,
            ParentCategoryNotFoundException.class,
            RoleAlreadyExistsException.class,


    })
    public ResponseEntity<BasicResponse> handleNotFoundBusinessExceptions(Exception ex, WebRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.NOT_FOUND);
    }


    // === Validation Exceptions === //

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BasicResponse> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return buildErrorResponse(errorMessage, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BasicResponse> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BasicResponse> handleSpringJsonParseException(
            HttpMessageNotReadableException ex) {
        BasicResponse error = new BasicResponse(Messages.INVALID_DATA);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BasicResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        return buildErrorResponse(Messages.REQUEST_NOT_SUPPORTED, request, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BasicResponse> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        String firstError = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(v -> v.getMessage())
                .orElse("Validation failed");
        return buildErrorResponse(firstError, request, HttpStatus.BAD_REQUEST);

    }

    // === Fallback Exceptions === //

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BasicResponse> handleRuntime(RuntimeException ex, WebRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BasicResponse> handleAll(Exception ex, WebRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BasicResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String parameterName = ex.getName();
        Object invalidValue = ex.getValue();
        BasicResponse response = new BasicResponse(
                Messages.INVALID_ID_FORMAT,
                "parameter = " + parameterName + ", value = " + invalidValue
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}