package com.peakprosys.institute.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record StudentRequest(

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100)
    String fullName,

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    String email,

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain 10 digits")
    String phoneNumber,

    @NotBlank(message = "Course is required")
    String course

) {}