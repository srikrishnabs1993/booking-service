package com.statista.code.challenge.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @NotBlank(message = "Booking ID cannot be blank")
    private String bookingId;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @Positive(message = "Price must be positive")
    private double price;

    @NotBlank(message = "Currency cannot be blank")
    @Size(min = 3, max = 3, message = "Currency should be a 3-letter code")
    private String currency;

    @NotNull(message = "Subscription start date cannot be null")
    private LocalDate subscriptionStartDate;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Department cannot be blank")
    private String department;
}
