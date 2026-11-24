package com.peakprosys.institute.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CourseRequest(

    @NotBlank(message = "Course name is required")
    @Size(max = 100, message = "Course name cannot exceed 100 characters")
    String courseName,

    @NotBlank(message = "Course code is required")
    @Size(max = 20, message = "Course code cannot exceed 20 characters")
    String courseCode,

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    String description,

    @NotNull(message = "Course duration is required")
    @Min(value = 1, message = "Course duration must be at least 1 week")
    Integer durationInWeeks,

    @NotNull(message = "Course fee is required")
    @DecimalMin(value = "0.00", message = "Course fee cannot be negative")
    @Digits(integer = 8, fraction = 2, message = "Course fee must have at most 8 integer and 2 decimal digits")
    BigDecimal courseFee,

    Boolean active
) {
}