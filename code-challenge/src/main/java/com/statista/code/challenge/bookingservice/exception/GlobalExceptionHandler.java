package com.statista.code.challenge.bookingservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler that catches and handles exceptions thrown by controllers.
 * This class provides centralized exception handling across all @RequestMapping methods.
 * It returns appropriate HTTP responses with meaningful messages for the client.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String BOOKING_NOT_FOUND_MESSAGE = "Booking not found.";
    private static final String DEPARTMENT_NOT_FOUND_MESSAGE = "Department not found.";
    private static final String GENERAL_ERROR_MESSAGE = "An unexpected error occurred.";

    public GlobalExceptionHandler() {
        logger.info("GlobalExceptionHandler has been registered");
    }

    /**
     * Handles validation exceptions that occur when the input data for a request is invalid.
     *
     * @param ex the exception thrown when validation fails
     * @return a ResponseEntity containing a map of field errors and their corresponding messages, with HTTP status 400 (Bad Request)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles exceptions when a booking is not found in the system.
     *
     * @param ex the exception thrown when a booking cannot be found
     * @return a ResponseEntity containing the exception message, with HTTP status 404 (Not Found)
     */
    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<String> handleBookingNotFoundException(BookingNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BOOKING_NOT_FOUND_MESSAGE + " " + ex.getMessage());
    }

    /**
     * Handles exceptions when a department is not found in the system.
     *
     * @param ex the exception thrown when a department cannot be found
     * @return a ResponseEntity containing the exception message, with HTTP status 400 (Bad Request)
     */
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<String> handleDepartmentNotFoundException(DepartmentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DEPARTMENT_NOT_FOUND_MESSAGE + " " + ex.getMessage());
    }

    /**
     * Handles any other exceptions that are not specifically handled by other methods.
     *
     * @param ex the general exception thrown during the execution of the application
     * @return a ResponseEntity containing a general error message, with HTTP status 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GENERAL_ERROR_MESSAGE + " " + ex.getMessage());
    }

    @PostMapping("/trigger-error")
    public ResponseEntity<String> triggerError() {
        throw new RuntimeException("Test exception");
    }

}
