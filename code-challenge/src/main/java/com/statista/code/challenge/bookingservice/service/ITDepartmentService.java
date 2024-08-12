package com.statista.code.challenge.bookingservice.service;

import com.statista.code.challenge.bookingservice.model.Booking;
import org.springframework.stereotype.Service;

@Service
public class ITDepartmentService implements DepartmentService {

    private static final String DEPARTMENT_NAME = "it";
    private static final String PROCESSING_BOOKING_IN_IT = "Processing booking in IT: ";
    private static final String BOOKING_ID_MUST_NOT_BE_NULL = "Booking and Booking ID must not be null";

    @Override
    public String doBusiness(Booking booking) {
        if (booking == null || booking.getBookingId() == null) {
            throw new IllegalArgumentException(BOOKING_ID_MUST_NOT_BE_NULL);
        }

        String result = processBooking(booking);

        return result;
    }

    @Override
    public String getDepartmentName() {
        return DEPARTMENT_NAME;
    }

    /**
     * Simulates processing the booking for the IT department.
     *
     * @param booking the booking to process
     * @return a string indicating the result of the processing
     */
    private String processBooking(Booking booking) {
        return PROCESSING_BOOKING_IN_IT + booking.getBookingId();
    }
}
