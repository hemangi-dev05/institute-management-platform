
package com.peakprosys.institute.service.impl;

import com.peakprosys.institute.dto.request.TrainerRequest;
import com.peakprosys.institute.dto.response.TrainerResponse;
import com.peakprosys.institute.entity.Trainer;
import com.peakprosys.institute.exception.TrainerNotFoundException;
import com.peakprosys.institute.repository.TrainerRepository;
import com.peakprosys.institute.service.TrainerService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public TrainerResponse createTrainer(TrainerRequest request) {

        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (trainerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Email is already registered"
            );
        }

        Trainer trainer = new Trainer(
                request.trainerName(),
                email,
                request.phoneNumber(),
                request.specialization(),
                request.qualification(),
                request.experienceInYears(),
                true
        );

        return mapToResponse(trainerRepository.save(trainer));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainerResponse> getAllTrainers() {

        return trainerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TrainerResponse getTrainerById(Long id) {

        return mapToResponse(findTrainer(id));
    }

    @Override
    public TrainerResponse updateTrainer(
            Long id,
            TrainerRequest request) {

        Trainer trainer = findTrainer(id);

        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (!trainer.getEmail().equalsIgnoreCase(email)
                && trainerRepository.existsByEmailAndTrainerIdNot(
                email, id)) {

            throw new IllegalArgumentException(
                    "Email is already registered"
            );
        }

        trainer.setTrainerName(request.trainerName());
        trainer.setEmail(email);
        trainer.setPhoneNumber(request.phoneNumber());
        trainer.setSpecialization(request.specialization());
        trainer.setQualification(request.qualification());
        trainer.setExperienceInYears(request.experienceInYears());

        return mapToResponse(trainerRepository.save(trainer));
    }

    @Override
    public void deleteTrainer(Long id) {

        trainerRepository.delete(findTrainer(id));
    }

    private Trainer findTrainer(Long id) {

        return trainerRepository.findById(id)
                .orElseThrow(() -> new TrainerNotFoundException(id));
    }

    private TrainerResponse mapToResponse(Trainer trainer) {

        return new TrainerResponse(
                trainer.getTrainerId(),
                trainer.getTrainerName(),
                trainer.getEmail(),
                trainer.getPhoneNumber(),
                trainer.getSpecialization(),
                trainer.getQualification(),
                trainer.getExperienceInYears(),
                trainer.getActive()
        );
    }
}
