package com.statista.code.challenge.bookingservice.exception;

/**
 * Exception thrown when a specific booking cannot be found in the system.
 * <p>
 * This exception is typically used to indicate that a booking, identified by a unique ID,
 * is not recognized or does not exist in the repository.
 */
public class BookingNotFoundException extends RuntimeException {

    /**
     * Constructs a new BookingNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception; must not be {@code null}
     */
    public BookingNotFoundException(String message) {
        super(message);
    }
}
