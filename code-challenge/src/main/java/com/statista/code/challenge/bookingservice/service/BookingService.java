package com.statista.code.challenge.bookingservice.service;

import com.statista.code.challenge.bookingservice.exception.BookingNotFoundException;
import com.statista.code.challenge.bookingservice.exception.DepartmentNotFoundException;
import com.statista.code.challenge.bookingservice.model.Booking;
import com.statista.code.challenge.bookingservice.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Service class responsible for managing bookings and delegating business logic to the appropriate department.
 * This service interacts with the booking repository to perform CRUD operations and handles business processes
 * specific to each department.
 */
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final Map<String, DepartmentService> departmentServices = new HashMap<>();

    /**
     * Constructs a new BookingService instance with the provided BookingRepository and a list of DepartmentService implementations.
     *
     * @param bookingRepository the repository used for managing bookings
     * @param departmentServiceList a list of department services responsible for handling department-specific business logic
     */
    @Autowired
    public BookingService(BookingRepository bookingRepository,
                          List<DepartmentService> departmentServiceList) {
        this.bookingRepository = bookingRepository;
        for (DepartmentService service : departmentServiceList) {
            departmentServices.put(service.getDepartmentName().toLowerCase(), service);
        }
    }

    /**
     * Creates a new booking and sends a confirmation email.
     *
     * @param booking the booking to be created; must not be {@code null}
     */
    public void createBooking(Booking booking) {
        bookingRepository.addBooking(booking);
        sendEmailForBooking(booking);
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param bookingId the ID of the booking to retrieve; must not be {@code null}
     * @return the booking with the specified ID
     * @throws BookingNotFoundException if no booking with the given ID is found
     */
    public Booking getBooking(String bookingId) {
        Booking booking = bookingRepository.getBooking(bookingId);
        if (booking == null) {
            throw new BookingNotFoundException("Booking not found for ID: " + bookingId);
        }
        return booking;
    }

    /**
     * Updates an existing booking with new details.
     *
     * @param bookingId the ID of the booking to update; must not be {@code null}
     * @param booking the updated booking details; must not be {@code null}
     */
    public void updateBooking(String bookingId, Booking booking) {
        bookingRepository.updateBooking(bookingId, booking);
    }

    /**
     * Processes a booking by delegating the business logic to the appropriate department.
     *
     * @param bookingId the ID of the booking to process; must not be {@code null}
     * @return a string message indicating the result of the processing
     * @throws BookingNotFoundException if no booking with the given ID is found
     * @throws DepartmentNotFoundException if the department associated with the booking is unknown
     */
    public String doBusiness(String bookingId) {
        Booking booking = getBooking(bookingId);
        DepartmentService service = departmentServices.get(booking.getDepartment().toLowerCase());
        if (service == null) {
            throw new DepartmentNotFoundException("Unknown department: " + booking.getDepartment());
        }
        return service.doBusiness(booking);
    }

    /**
     * Retrieves a list of booking IDs associated with a specific department.
     *
     * @param department the name of the department; must not be {@code null}
     * @return a list of booking IDs associated with the specified department
     */
    public List<String> getBookingsByDepartment(String department) {
        return bookingRepository.getBookingsByDepartment(department);
    }

    /**
     * Retrieves a set of all unique currencies used in the bookings.
     *
     * @return a set of unique currency codes
     */
    public Set<String> getAllCurrencies() {
        return bookingRepository.getAllCurrencies();
    }

    /**
     * Calculates the total sum of prices for bookings in a specific currency.
     *
     * @param currency the currency code to sum prices for; must not be {@code null}
     * @return the total sum of prices for the specified currency
     */
    public double getSumByCurrency(String currency) {
        return bookingRepository.getSumByCurrency(currency);
    }

    /**
     * Sends a confirmation email for the given booking.
     * <p>
     * This is a placeholder method simulating the sending of an email.
     *
     * @param booking the booking for which to send an email; must not be {@code null}
     */
    private void sendEmailForBooking(Booking booking) {
        System.out.println("Sending email for booking: " + booking);
    }
}
