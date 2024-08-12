package com.statista.code.challenge.bookingservice.controller;

import com.statista.code.challenge.bookingservice.model.Booking;
import com.statista.code.challenge.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * REST controller for managing bookings. Provides endpoints for creating, updating,
 * retrieving, and processing bookings.
 */
@RestController
@RequestMapping("/bookingservice")
public class BookingController {

    private final BookingService bookingService;

    // Constants for response messages
    private static final String BOOKING_NOT_FOUND_MESSAGE = "Booking not found";
    private static final String UNKNOWN_DEPARTMENT_MESSAGE = "Unknown department";

    /**
     * Constructs a new BookingController with the specified BookingService.
     *
     * @param bookingService the service used to manage bookings
     */
    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Creates a new booking.
     *
     * @param booking the booking to create; must be valid
     * @return a ResponseEntity with HTTP status 201 (Created)
     */
    @PostMapping("/bookings")
    public ResponseEntity<Void> createBooking(@Valid @RequestBody Booking booking) {
        bookingService.createBooking(booking);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Updates an existing booking.
     *
     * @param bookingId the ID of the booking to update; must not be null
     * @param booking the updated booking details; must be valid
     * @return a ResponseEntity with HTTP status 200 (OK)
     */
    @PutMapping("/bookings/{bookingId}")
    public ResponseEntity<Void> updateBooking(@PathVariable String bookingId, @Valid @RequestBody Booking booking) {
        bookingService.updateBooking(bookingId, booking);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param bookingId the ID of the booking to retrieve; must not be null
     * @return a ResponseEntity containing the booking and HTTP status 200 (OK), or HTTP status 404 (Not Found) if not found
     */
    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<Booking> getBooking(@PathVariable String bookingId) {
        Booking booking = bookingService.getBooking(bookingId);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a list of booking IDs associated with a specific department.
     *
     * @param department the name of the department; must not be null
     * @return a ResponseEntity containing a list of booking IDs and HTTP status 200 (OK), or HTTP status 404 (Not Found) if no bookings found
     */
    @GetMapping("/bookings/department/{department}")
    public ResponseEntity<List<String>> getBookingsByDepartment(@PathVariable String department) {
        List<String> bookings = bookingService.getBookingsByDepartment(department);
        if (bookings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(bookings);
    }

    /**
     * Retrieves a set of all unique currencies used in the bookings.
     *
     * @return a ResponseEntity containing a set of unique currencies and HTTP status 200 (OK)
     */
    @GetMapping("/bookings/currencies")
    public ResponseEntity<Set<String>> getAllCurrencies() {
        Set<String> currencies = bookingService.getAllCurrencies();
        return ResponseEntity.ok(currencies);
    }

    /**
     * Calculates the total sum of prices for bookings in a specific currency.
     *
     * @param currency the currency code; must not be null
     * @return a ResponseEntity containing the total sum and HTTP status 200 (OK)
     */
    @GetMapping("/sum/{currency}")
    public ResponseEntity<Double> getSumByCurrency(@PathVariable String currency) {
        double sum = bookingService.getSumByCurrency(currency);
        return ResponseEntity.ok(sum);
    }

    /**
     * Processes a booking by delegating the business logic to the appropriate department.
     *
     * @param bookingId the ID of the booking to process; must not be null
     * @return a ResponseEntity containing the result of the processing and HTTP status 200 (OK),
     *         or HTTP status 404 (Not Found) if the booking is not found, or HTTP status 400 (Bad Request) if the department is unknown
     */
    @GetMapping("/bookings/dobusiness/{bookingId}")
    public ResponseEntity<String> doBusiness(@PathVariable String bookingId) {
        String result = bookingService.doBusiness(bookingId);
        if (BOOKING_NOT_FOUND_MESSAGE.equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else if (result.startsWith(UNKNOWN_DEPARTMENT_MESSAGE)) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/test-validation")
    public ResponseEntity<String> testValidation(@Valid @RequestBody Booking booking) {
        return ResponseEntity.ok("Validation passed");
    }

}
