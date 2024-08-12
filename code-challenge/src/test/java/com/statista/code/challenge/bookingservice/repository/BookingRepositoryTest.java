package com.statista.code.challenge.bookingservice.repository;

import com.statista.code.challenge.bookingservice.model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookingRepositoryTest {

    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        bookingRepository = new BookingRepository();
    }

    @Test
    void addBooking_ShouldAddBooking() {
        Booking booking = new Booking("1", "Test Booking", 100.0, "USD", LocalDate.now(), "test@example.com", "sales");

        bookingRepository.addBooking(booking);

        Booking retrievedBooking = bookingRepository.getBooking("1");
        assertEquals(booking, retrievedBooking);
    }

    @Test
    void getBooking_ShouldReturnBooking_WhenBookingExists() {
        Booking booking = new Booking("1", "Test Booking", 100.0, "USD", LocalDate.now(), "test@example.com", "sales");
        bookingRepository.addBooking(booking);

        Booking retrievedBooking = bookingRepository.getBooking("1");

        assertNotNull(retrievedBooking);
        assertEquals("Test Booking", retrievedBooking.getDescription());
    }

    @Test
    void getBooking_ShouldReturnNull_WhenBookingDoesNotExist() {
        Booking retrievedBooking = bookingRepository.getBooking("1");

        assertNull(retrievedBooking);
    }

    @Test
    void getAllBookings_ShouldReturnAllBookings() {
        Booking booking1 = new Booking("1", "Booking 1", 100.0, "USD", LocalDate.now(), "test1@example.com", "sales");
        Booking booking2 = new Booking("2", "Booking 2", 150.0, "EUR", LocalDate.now(), "test2@example.com", "support");

        bookingRepository.addBooking(booking1);
        bookingRepository.addBooking(booking2);

        Collection<Booking> allBookings = bookingRepository.getAllBookings();

        assertEquals(2, allBookings.size());
        assertTrue(allBookings.contains(booking1));
        assertTrue(allBookings.contains(booking2));
    }

    @Test
    void updateBooking_ShouldUpdateBooking() {
        Booking booking = new Booking("1", "Test Booking", 100.0, "USD", LocalDate.now(), "test@example.com", "sales");
        bookingRepository.addBooking(booking);

        Booking updatedBooking = new Booking("1", "Updated Booking", 150.0, "EUR", LocalDate.now(), "update@example.com", "support");
        bookingRepository.updateBooking("1", updatedBooking);

        Booking retrievedBooking = bookingRepository.getBooking("1");
        assertEquals(updatedBooking, retrievedBooking);
    }

    @Test
    void getBookingsByDepartment_ShouldReturnBookingIds_WhenDepartmentMatches() {
        Booking booking1 = new Booking("1", "Booking 1", 100.0, "USD", LocalDate.now(), "test1@example.com", "sales");
        Booking booking2 = new Booking("2", "Booking 2", 150.0, "EUR", LocalDate.now(), "test2@example.com", "support");

        bookingRepository.addBooking(booking1);
        bookingRepository.addBooking(booking2);

        List<String> salesBookings = bookingRepository.getBookingsByDepartment("sales");

        assertEquals(1, salesBookings.size());
        assertTrue(salesBookings.contains("1"));
    }

    @Test
    void getAllCurrencies_ShouldReturnUniqueCurrencies() {
        Booking booking1 = new Booking("1", "Booking 1", 100.0, "USD", LocalDate.now(), "test1@example.com", "sales");
        Booking booking2 = new Booking("2", "Booking 2", 150.0, "USD", LocalDate.now(), "test2@example.com", "support");
        Booking booking3 = new Booking("3", "Booking 3", 200.0, "EUR", LocalDate.now(), "test3@example.com", "it");

        bookingRepository.addBooking(booking1);
        bookingRepository.addBooking(booking2);
        bookingRepository.addBooking(booking3);

        Set<String> currencies = bookingRepository.getAllCurrencies();

        assertEquals(2, currencies.size());
        assertTrue(currencies.contains("USD"));
        assertTrue(currencies.contains("EUR"));
    }

    @Test
    void getSumByCurrency_ShouldReturnTotalSumOfPricesForGivenCurrency() {
        Booking booking1 = new Booking("1", "Booking 1", 100.0, "USD", LocalDate.now(), "test1@example.com", "sales");
        Booking booking2 = new Booking("2", "Booking 2", 150.0, "USD", LocalDate.now(), "test2@example.com", "support");
        Booking booking3 = new Booking("3", "Booking 3", 200.0, "EUR", LocalDate.now(), "test3@example.com", "it");

        bookingRepository.addBooking(booking1);
        bookingRepository.addBooking(booking2);
        bookingRepository.addBooking(booking3);

        double sumUSD = bookingRepository.getSumByCurrency("USD");
        double sumEUR = bookingRepository.getSumByCurrency("EUR");

        assertEquals(250.0, sumUSD, 0.001);
        assertEquals(200.0, sumEUR, 0.001);
    }
}
