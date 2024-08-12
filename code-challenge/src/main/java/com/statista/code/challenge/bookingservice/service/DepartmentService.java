package com.statista.code.challenge.bookingservice.service;

import com.statista.code.challenge.bookingservice.model.Booking;

/**
 * Interface representing a department service in the booking system.
 * Each department that implements this interface will handle specific business logic
 * associated with processing bookings and providing department-specific services.
 */
public interface DepartmentService {

    /**
     * Processes the given booking according to the business rules of the department.
     *
     * @param booking the booking to be processed; must not be {@code null}
     * @return a string message indicating the result of the processing
     * @throws IllegalArgumentException if the booking is {@code null} or if any required booking data is missing
     */
    String doBusiness(Booking booking);

    /**
     * Returns the name of the department.
     * The department name should be unique and used to identify the department within the system.
     *
     * @return the name of the department; must not be {@code null} or empty
     */
    String getDepartmentName();
}
