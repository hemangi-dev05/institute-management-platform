
package com.peakprosys.institute.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TrainerRequest(

        @NotBlank(message = "Trainer name is required")
        @Size(min = 2, max = 100)
        String trainerName,

        @NotBlank(message = "Email is required")
        @Email(message = "Enter a valid email")
        @Size(max = 150)
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^[0-9]{10}$",
                message = "Phone number must contain 10 digits")
        String phoneNumber,

        @NotBlank(message = "Specialization is required")
        @Size(max = 100)
        String specialization,

        @NotBlank(message = "Qualification is required")
        @Size(max = 100)
        String qualification,

        @NotNull(message = "Experience is required")
        @Min(value = 0, message = "Experience cannot be negative")
        Integer experienceInYears

) {}
