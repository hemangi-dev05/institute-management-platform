package com.peakprosys.institute.dto.response;

import java.math.BigDecimal;

public record CourseResponse(
    Long id,
    String courseName,
    String courseCode,
    String description,
    Integer durationInWeeks,
    BigDecimal courseFee,
    Boolean active
) {
}