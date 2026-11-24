
package com.peakprosys.institute.service;

import com.peakprosys.institute.dto.request.TrainerRequest;
import com.peakprosys.institute.dto.response.TrainerResponse;

import java.util.List;

public interface TrainerService {

    TrainerResponse createTrainer(TrainerRequest request);

    List<TrainerResponse> getAllTrainers();

    TrainerResponse getTrainerById(Long id);

    TrainerResponse updateTrainer(Long id, TrainerRequest request);

    void deleteTrainer(Long id);
}
