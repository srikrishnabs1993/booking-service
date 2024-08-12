package com.statista.code.challenge.bookingservice.exception;

/**
 * Exception thrown when a specific department cannot be found in the system.
 * <p>
 * This exception is typically used to indicate that a department associated with a booking
 * or operation is not recognized or does not exist.
 */
public class DepartmentNotFoundException extends RuntimeException {

    /**
     * Constructs a new DepartmentNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception; must not be {@code null}
     */
    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
