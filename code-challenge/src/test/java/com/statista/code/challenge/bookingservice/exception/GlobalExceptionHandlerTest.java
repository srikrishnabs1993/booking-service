package com.statista.code.challenge.bookingservice.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleValidationExceptions() {
        // Prepare mock validation exception
        FieldError fieldError = new FieldError("booking", "email", "Email should be valid");
        BindException bindException = new BindException(new Object(), "booking");
        bindException.addError(fieldError);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException((MethodParameter) null, bindException);

        // Call the method under test
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(ex);

        // Assert the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Email should be valid", response.getBody().get("email"));
    }

    @Test
    void handleBookingNotFoundException() {
        // Prepare exception
        BookingNotFoundException ex = new BookingNotFoundException("123");

        // Call the method under test
        ResponseEntity<String> response = globalExceptionHandler.handleBookingNotFoundException(ex);

        // Assert the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Booking not found. 123", response.getBody());
    }

    @Test
    void handleDepartmentNotFoundException() {
        // Prepare exception
        DepartmentNotFoundException ex = new DepartmentNotFoundException("Unknown");

        // Call the method under test
        ResponseEntity<String> response = globalExceptionHandler.handleDepartmentNotFoundException(ex);

        // Assert the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Department not found. Unknown", response.getBody());
    }

    @Test
    void handleGeneralExceptions() {
        // Prepare exception
        Exception ex = new Exception("General error");

        // Call the method under test
        ResponseEntity<String> response = globalExceptionHandler.handleGeneralExceptions(ex);

        // Assert the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred. General error", response.getBody());
    }

    @Test
    void triggerError_shouldThrowRuntimeException() {
        // This test ensures the triggerError method throws an exception
        RuntimeException exception = new RuntimeException("Test exception");

        // Assertions
        try {
            globalExceptionHandler.triggerError();
        } catch (RuntimeException ex) {
            assertEquals("Test exception", ex.getMessage());
        }
    }
}
