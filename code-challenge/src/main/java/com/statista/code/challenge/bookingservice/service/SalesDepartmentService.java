package com.statista.code.challenge.bookingservice.service;

import com.statista.code.challenge.bookingservice.model.Booking;
import org.springframework.stereotype.Service;

@Service
public class SalesDepartmentService implements DepartmentService {

    private static final String DEPARTMENT_NAME = "sales";
    private static final String BOOKING_AND_BOOKING_ID_MUST_NOT_BE_NULL = "Booking and Booking ID must not be null";
    private static final String BOOKING_IN_SALES = "Processing booking in Sales: ";

    @Override
    public String doBusiness(Booking booking) {
        if (booking == null || booking.getBookingId() == null) {
            throw new IllegalArgumentException(BOOKING_AND_BOOKING_ID_MUST_NOT_BE_NULL);
        }

        String result = processBooking(booking);

        return result;
    }

    @Override
    public String getDepartmentName() {
        return DEPARTMENT_NAME;
    }

    /**
     * Simulates processing the booking for the Sales department.
     *
     * @param booking the booking to process
     * @return a string indicating the result of the processing
     */
    private String processBooking(Booking booking) {
        return BOOKING_IN_SALES + booking.getBookingId();
    }
}
