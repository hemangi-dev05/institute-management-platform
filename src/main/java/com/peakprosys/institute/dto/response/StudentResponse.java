package com.peakprosys.institute.dto.response;

public record StudentResponse(
    Long studentId,
    String fullName,
    String email,
    String phoneNumber,
    String course
) {}