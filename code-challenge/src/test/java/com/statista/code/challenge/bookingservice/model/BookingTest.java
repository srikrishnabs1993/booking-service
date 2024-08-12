package com.statista.code.challenge.bookingservice.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookingTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        Booking booking = new Booking("1", "Test Booking", 100.0, "USD", LocalDate.now(), "test@example.com", "sales");

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertTrue(violations.isEmpty());
    }

    @Test
    void whenBookingIdIsBlank_thenViolationOccurs() {
        Booking booking = new Booking("", "Test Booking", 100.0, "USD", LocalDate.now(), "test@example.com", "sales");

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertEquals(1, violations.size());
        assertEquals("Booking ID cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenDescriptionIsBlank_thenViolationOccurs() {
        Booking booking = new Booking("1", "", 100.0, "USD", LocalDate.now(), "test@example.com", "sales");

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertEquals(1, violations.size());
        assertEquals("Description cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenDescriptionExceedsMaxLength_thenViolationOccurs() {
        String longDescription = "a".repeat(256); // 256 characters long
        Booking booking = new Booking("1", longDescription, 100.0, "USD", LocalDate.now(), "test@example.com", "sales");

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertEquals(1, violations.size());
        assertEquals("Description cannot exceed 255 characters", violations.iterator().next().getMessage());
    }

    @Test
    void whenPriceIsNotPositive_thenViolationOccurs() {
        Booking booking = new Booking("1", "Test Booking", -100.0, "USD", LocalDate.now(), "test@example.com", "sales");

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertEquals(1, violations.size());
        assertEquals("Price must be positive", violations.iterator().next().getMessage());
    }

    @Test
    void whenCurrencyIsNotThreeLetters_thenViolationOccurs() {
        Booking booking = new Booking("1", "Test Booking", 100.0, "US", LocalDate.now(), "test@example.com", "sales");

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertEquals(1, violations.size());
        assertEquals("Currency should be a 3-letter code", violations.iterator().next().getMessage());
    }

    @Test
    void whenSubscriptionStartDateIsNull_thenViolationOccurs() {
        Booking booking = new Booking("1", "Test Booking", 100.0, "USD", null, "test@example.com", "sales");

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertEquals(1, violations.size());
        assertEquals("Subscription start date cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    void whenEmailIsBlank_thenViolationOccurs() {
        Booking booking = new Booking("1", "Test Booking", 100.0, "USD", LocalDate.now(), "", "sales");

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertEquals(1, violations.size());
        assertEquals("Email cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenEmailIsInvalid_thenViolationOccurs() {
        Booking booking = new Booking("1", "Test Booking", 100.0, "USD", LocalDate.now(), "invalid-email", "sales");

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertEquals(1, violations.size());
        assertEquals("Email should be valid", violations.iterator().next().getMessage());
    }

    @Test
    void whenDepartmentIsBlank_thenViolationOccurs() {
        Booking booking = new Booking("1", "Test Booking", 100.0, "USD", LocalDate.now(), "test@example.com", "");

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertEquals(1, violations.size());
        assertEquals("Department cannot be blank", violations.iterator().next().getMessage());
    }
}
