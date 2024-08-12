package com.statista.code.challenge.bookingservice.controller;

import com.statista.code.challenge.bookingservice.model.Booking;
import com.statista.code.challenge.bookingservice.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBooking_ShouldReturnCreatedStatus() {
        Booking booking = new Booking("1", "Test Booking", 100.0, "USD", LocalDate.now(), "test@example.com", "sales");

        ResponseEntity<Void> response = bookingController.createBooking(booking);

        verify(bookingService, times(1)).createBooking(booking);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void updateBooking_ShouldReturnOkStatus() {
        Booking booking = new Booking("1", "Updated Booking", 150.0, "EUR", LocalDate.now(), "update@example.com", "support");

        ResponseEntity<Void> response = bookingController.updateBooking("1", booking);

        verify(bookingService, times(1)).updateBooking("1", booking);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getBooking_ShouldReturnBooking_WhenBookingExists() {
        Booking booking = new Booking("1", "Test Booking", 100.0, "USD", LocalDate.now(), "test@example.com", "sales");
        when(bookingService.getBooking("1")).thenReturn(booking);

        ResponseEntity<Booking> response = bookingController.getBooking("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(booking, response.getBody());
    }

    @Test
    void getBooking_ShouldReturnNotFound_WhenBookingDoesNotExist() {
        when(bookingService.getBooking("1")).thenReturn(null);

        ResponseEntity<Booking> response = bookingController.getBooking("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getBookingsByDepartment_ShouldReturnListOfBookingIds_WhenDepartmentHasBookings() {
        List<String> bookingIds = Arrays.asList("1", "2");
        when(bookingService.getBookingsByDepartment("sales")).thenReturn(bookingIds);

        ResponseEntity<List<String>> response = bookingController.getBookingsByDepartment("sales");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(bookingIds, response.getBody());
    }

    @Test
    void getBookingsByDepartment_ShouldReturnNotFound_WhenNoBookingsInDepartment() {
        when(bookingService.getBookingsByDepartment("sales")).thenReturn(Arrays.asList());

        ResponseEntity<List<String>> response = bookingController.getBookingsByDepartment("sales");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllCurrencies_ShouldReturnListOfCurrencies() {
        Set<String> currencies = new HashSet<>(Arrays.asList("USD", "EUR"));
        when(bookingService.getAllCurrencies()).thenReturn(currencies);

        ResponseEntity<Set<String>> response = bookingController.getAllCurrencies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(currencies, response.getBody());
    }

    @Test
    void getSumByCurrency_ShouldReturnSumOfPricesInCurrency() {
        when(bookingService.getSumByCurrency("USD")).thenReturn(250.0);

        ResponseEntity<Double> response = bookingController.getSumByCurrency("USD");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(250.0, response.getBody());
    }

    @Test
    void doBusiness_ShouldReturnResult_WhenBookingIsProcessed() {
        when(bookingService.doBusiness("1")).thenReturn("Processed in Sales");

        ResponseEntity<String> response = bookingController.doBusiness("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Processed in Sales", response.getBody());
    }

    @Test
    void doBusiness_ShouldReturnNotFound_WhenBookingIsNotFound() {
        when(bookingService.doBusiness("1")).thenReturn("Booking not found");

        ResponseEntity<String> response = bookingController.doBusiness("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Booking not found", response.getBody());
    }

    @Test
    void doBusiness_ShouldReturnBadRequest_WhenDepartmentIsUnknown() {
        when(bookingService.doBusiness("1")).thenReturn("Unknown department for booking: 1");

        ResponseEntity<String> response = bookingController.doBusiness("1");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unknown department for booking: 1", response.getBody());
    }
}
