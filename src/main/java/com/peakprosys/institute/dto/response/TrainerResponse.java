package com.peakprosys.institute.dto.response;

public record TrainerResponse(
        Long trainerId,
        String trainerName,
        String email,
        String phoneNumber,
        String specialization,
        String qualification,
        Integer experienceInYears,
        Boolean active
) {}