package com.statista.code.challenge.bookingservice.repository;

import com.statista.code.challenge.bookingservice.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Repository class for managing bookings. This class provides methods to add, retrieve,
 * update, and query bookings within an in-memory data structure.
 * <p>
 * The repository uses a {@link LinkedHashMap} to store bookings, ensuring that the
 * insertion order is maintained. This implementation is suitable for applications where
 * data persistence is not a requirement, such as in-memory testing or simple use cases.
 */
@Repository
public class BookingRepository {

    private final Map<String, Booking> bookings = new LinkedHashMap<>();

    /**
     * Adds a new booking to the repository.
     *
     * @param booking the booking to be added; must not be {@code null}
     */
    public void addBooking(Booking booking) {
        bookings.put(booking.getBookingId(), booking);
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param bookingId the ID of the booking to retrieve; must not be {@code null}
     * @return the booking with the specified ID, or {@code null} if no booking with the given ID exists
     */
    public Booking getBooking(String bookingId) {
        return bookings.get(bookingId);
    }

    /**
     * Retrieves all bookings in the repository.
     *
     * @return a collection of all bookings
     */
    public Collection<Booking> getAllBookings() {
        return bookings.values();
    }

    /**
     * Updates an existing booking with new details.
     * If the booking ID does not exist, this method will add the booking as a new entry.
     *
     * @param bookingId the ID of the booking to update; must not be {@code null}
     * @param booking the updated booking details; must not be {@code null}
     */
    public void updateBooking(String bookingId, Booking booking) {
        bookings.put(bookingId, booking);
    }

    /**
     * Retrieves a list of booking IDs associated with a specific department.
     *
     * @param department the name of the department to filter bookings by; must not be {@code null}
     * @return a list of booking IDs for the specified department
     */
    public List<String> getBookingsByDepartment(String department) {
        List<String> result = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getDepartment().equalsIgnoreCase(department)) {
                result.add(booking.getBookingId());
            }
        }
        return result;
    }

    /**
     * Retrieves a set of all unique currencies used in the bookings.
     *
     * @return a set of unique currency codes
     */
    public Set<String> getAllCurrencies() {
        Set<String> currencies = new HashSet<>();
        for (Booking booking : bookings.values()) {
            currencies.add(booking.getCurrency());
        }
        return currencies;
    }

    /**
     * Calculates the total sum of prices for bookings in a specific currency.
     *
     * @param currency the currency code to sum prices for; must not be {@code null}
     * @return the total sum of prices for the specified currency
     */
    public double getSumByCurrency(String currency) {
        double sum = 0.0;
        for (Booking booking : bookings.values()) {
            if (booking.getCurrency().equalsIgnoreCase(currency)) {
                sum += booking.getPrice();
            }
        }
        return sum;
    }
}
