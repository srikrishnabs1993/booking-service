package com.statista.code.challenge.bookingservice.service;

import com.statista.code.challenge.bookingservice.exception.BookingNotFoundException;
import com.statista.code.challenge.bookingservice.exception.DepartmentNotFoundException;
import com.statista.code.challenge.bookingservice.model.Booking;
import com.statista.code.challenge.bookingservice.repository.BookingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private SalesDepartmentService salesDepartmentService;

    @Mock
    private SupportDepartmentService supportDepartmentService;

    @Mock
    private ITDepartmentService itDepartmentService;

    private BookingService bookingService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        // Mock the getDepartmentName method for each service
        when(salesDepartmentService.getDepartmentName()).thenReturn("sales");
        when(supportDepartmentService.getDepartmentName()).thenReturn("support");
        when(itDepartmentService.getDepartmentName()).thenReturn("it");

        // Initialize the departmentServiceList with mock services
        List<DepartmentService> departmentServices = Arrays.asList(salesDepartmentService, supportDepartmentService, itDepartmentService);

        bookingService = new BookingService(bookingRepository, departmentServices);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void createBooking_ShouldAddBookingAndSendEmail() {
        Booking booking = new Booking("1", "Test Booking", 100.0, "USD", LocalDate.now(), "test@example.com", "sales");

        bookingService.createBooking(booking);

        verify(bookingRepository, times(1)).addBooking(booking);
    }

    @Test
    void getBooking_ShouldReturnBooking_WhenBookingExists() {
        Booking booking = new Booking("1", "Test Booking", 100.0, "USD", LocalDate.now(), "test@example.com", "sales");
        when(bookingRepository.getBooking("1")).thenReturn(booking);

        Booking result = bookingService.getBooking("1");

        assertNotNull(result);
        assertEquals("Test Booking", result.getDescription());
    }

    @Test
    void getBooking_ShouldThrowBookingNotFoundException_WhenBookingDoesNotExist() {
        when(bookingRepository.getBooking("1")).thenReturn(null);

        assertThrows(BookingNotFoundException.class, () -> bookingService.getBooking("1"));
    }

    @Test
    void updateBooking_ShouldUpdateBooking_WhenBookingExists() {
        Booking booking = new Booking("1", "Updated Booking", 150.0, "EUR", LocalDate.now(), "update@example.com", "support");

        bookingService.updateBooking("1", booking);

        verify(bookingRepository, times(1)).updateBooking("1", booking);
    }

    @Test
    void doBusiness_ShouldReturnBusinessResult_WhenDepartmentIsValid() {
        Booking booking = new Booking("1", "Sales Booking", 100.0, "USD", LocalDate.now(), "sales@example.com", "sales");
        when(bookingRepository.getBooking("1")).thenReturn(booking);
        when(salesDepartmentService.doBusiness(booking)).thenReturn("Processed in Sales");

        String result = bookingService.doBusiness("1");

        assertEquals("Processed in Sales", result);
    }

    @Test
    void doBusiness_ShouldThrowDepartmentNotFoundException_WhenDepartmentIsInvalid() {
        Booking booking = new Booking("2", "Unknown Booking", 100.0, "USD", LocalDate.now(), "unknown@example.com", "unknown");
        when(bookingRepository.getBooking("2")).thenReturn(booking);

        assertThrows(DepartmentNotFoundException.class, () -> bookingService.doBusiness("2"));
    }

    @Test
    void getBookingsByDepartment_ShouldReturnBookingIds_WhenDepartmentIsValid() {
        when(bookingRepository.getBookingsByDepartment("sales")).thenReturn(Arrays.asList("1", "2"));

        List<String> result = bookingService.getBookingsByDepartment("sales");

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getAllCurrencies_ShouldReturnAllCurrencies() {
        Set<String> currencies = new HashSet<>(Arrays.asList("USD", "EUR"));
        when(bookingRepository.getAllCurrencies()).thenReturn(currencies);

        Set<String> result = bookingService.getAllCurrencies();

        assertEquals(2, result.size());
        assertTrue(result.contains("USD"));
        assertTrue(result.contains("EUR"));
    }

    @Test
    void getSumByCurrency_ShouldReturnSumOfPricesInGivenCurrency() {
        when(bookingRepository.getSumByCurrency("USD")).thenReturn(300.0);

        double sum = bookingService.getSumByCurrency("USD");

        assertEquals(300.0, sum, 0.001);
    }
}
