
package com.peakprosys.institute.service;

import com.peakprosys.institute.dto.request.TrainerRequest;
import com.peakprosys.institute.dto.response.TrainerResponse;
import com.peakprosys.institute.entity.Trainer;
import com.peakprosys.institute.exception.TrainerNotFoundException;
import com.peakprosys.institute.repository.TrainerRepository;
import com.peakprosys.institute.service.impl.TrainerServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    // 1. Create Trainer successfully
    @Test
    void shouldCreateTrainerSuccessfully() {

        TrainerRequest request = new TrainerRequest(
                "Hemangi Pandey",
                "  HEMANGI@EXAMPLE.COM  ",
                "9876543210",
                "Java Development",
                "BE Computer Engineering",
                2
        );

        when(trainerRepository.existsByEmail("hemangi@example.com"))
                .thenReturn(false);

        when(trainerRepository.save(any(Trainer.class)))
                .thenAnswer(invocation -> {
                    Trainer trainer = invocation.getArgument(0);
                    trainer.setTrainerId(1L);
                    return trainer;
                });

        TrainerResponse response = trainerService.createTrainer(request);

        assertNotNull(response);
        assertEquals(1L, response.trainerId());
        assertEquals("Hemangi Pandey", response.trainerName());
        assertEquals("hemangi@example.com", response.email());
        assertEquals("Java Development", response.specialization());
        assertEquals(2, response.experienceInYears());
        assertTrue(response.active());

        verify(trainerRepository).existsByEmail("hemangi@example.com");
        verify(trainerRepository).save(any(Trainer.class));
    }

    // 2. Reject duplicate email during creation
    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        TrainerRequest request = new TrainerRequest(
                "Test Trainer",
                "trainer@example.com",
                "9876543210",
                "Java Development",
                "MCA",
                3
        );

        when(trainerRepository.existsByEmail("trainer@example.com"))
                .thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> trainerService.createTrainer(request)
        );

        assertEquals(
                "Email is already registered",
                exception.getMessage()
        );

        verify(trainerRepository).existsByEmail("trainer@example.com");
        verify(trainerRepository, never()).save(any(Trainer.class));
    }

    // 3. Return all Trainers
    @Test
    void shouldReturnAllTrainers() {

        Trainer trainer1 = new Trainer(
                "Trainer One",
                "one@example.com",
                "9876543210",
                "Java",
                "MCA",
                3,
                true
        );
        trainer1.setTrainerId(1L);

        Trainer trainer2 = new Trainer(
                "Trainer Two",
                "two@example.com",
                "9876543211",
                "Python",
                "BE",
                5,
                true
        );
        trainer2.setTrainerId(2L);

        when(trainerRepository.findAll())
                .thenReturn(List.of(trainer1, trainer2));

        List<TrainerResponse> response = trainerService.getAllTrainers();

        assertEquals(2, response.size());
        assertEquals("Trainer One", response.get(0).trainerName());
        assertEquals("Trainer Two", response.get(1).trainerName());

        verify(trainerRepository).findAll();
    }

    // 4. Return Trainer by ID
    @Test
    void shouldReturnTrainerWhenTrainerExists() {

        Trainer trainer = new Trainer(
                "Hemangi Pandey",
                "hemangi@example.com",
                "9876543210",
                "Java Development",
                "BE",
                2,
                true
        );
        trainer.setTrainerId(1L);

        when(trainerRepository.findById(1L))
                .thenReturn(Optional.of(trainer));

        TrainerResponse response = trainerService.getTrainerById(1L);

        assertNotNull(response);
        assertEquals(1L, response.trainerId());
        assertEquals("Hemangi Pandey", response.trainerName());
        assertEquals("hemangi@example.com", response.email());

        verify(trainerRepository).findById(1L);
    }

    // 5. Throw exception when Trainer does not exist
    @Test
    void shouldThrowExceptionWhenTrainerDoesNotExist() {

        when(trainerRepository.findById(999L))
                .thenReturn(Optional.empty());

        TrainerNotFoundException exception = assertThrows(
                TrainerNotFoundException.class,
                () -> trainerService.getTrainerById(999L)
        );

        assertEquals(
                "Trainer not found with ID: 999",
                exception.getMessage()
        );

        verify(trainerRepository).findById(999L);
    }

    // 6. Update Trainer successfully
    @Test
    void shouldUpdateTrainerSuccessfully() {

        Trainer existingTrainer = new Trainer(
                "Old Name",
                "old@example.com",
                "9876543210",
                "Java",
                "MCA",
                2,
                true
        );
        existingTrainer.setTrainerId(1L);

        TrainerRequest request = new TrainerRequest(
                "Updated Name",
                "updated@example.com",
                "9876543211",
                "Spring Boot",
                "BE Computer Engineering",
                5
        );

        when(trainerRepository.findById(1L))
                .thenReturn(Optional.of(existingTrainer));

        when(trainerRepository.existsByEmailAndTrainerIdNot(
                "updated@example.com", 1L))
                .thenReturn(false);

        when(trainerRepository.save(any(Trainer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TrainerResponse response = trainerService.updateTrainer(1L, request);

        assertEquals("Updated Name", response.trainerName());
        assertEquals("updated@example.com", response.email());
        assertEquals("Spring Boot", response.specialization());
        assertEquals(5, response.experienceInYears());
        assertTrue(response.active());

        verify(trainerRepository).findById(1L);
        verify(trainerRepository).existsByEmailAndTrainerIdNot(
                "updated@example.com", 1L);
        verify(trainerRepository).save(existingTrainer);
    }

    // 7. Reject duplicate email during update
    @Test
    void shouldThrowExceptionWhenUpdatingWithExistingEmail() {

        Trainer existingTrainer = new Trainer(
                "Trainer One",
                "one@example.com",
                "9876543210",
                "Java",
                "MCA",
                3,
                true
        );
        existingTrainer.setTrainerId(1L);

        TrainerRequest request = new TrainerRequest(
                "Trainer One",
                "two@example.com",
                "9876543210",
                "Java",
                "MCA",
                3
        );

        when(trainerRepository.findById(1L))
                .thenReturn(Optional.of(existingTrainer));

        when(trainerRepository.existsByEmailAndTrainerIdNot(
                "two@example.com", 1L))
                .thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> trainerService.updateTrainer(1L, request)
        );

        assertEquals(
                "Email is already registered",
                exception.getMessage()
        );

        verify(trainerRepository, never()).save(any(Trainer.class));
    }

    // 8. Allow Trainer to retain their existing email
    @Test
    void shouldAllowTrainerToKeepSameEmailDuringUpdate() {

        Trainer existingTrainer = new Trainer(
                "Old Name",
                "trainer@example.com",
                "9876543210",
                "Java",
                "MCA",
                3,
                true
        );
        existingTrainer.setTrainerId(1L);

        TrainerRequest request = new TrainerRequest(
                "Updated Name",
                "TRAINER@EXAMPLE.COM",
                "9876543211",
                "Spring Boot",
                "BE",
                4
        );

        when(trainerRepository.findById(1L))
                .thenReturn(Optional.of(existingTrainer));

        when(trainerRepository.save(any(Trainer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TrainerResponse response = trainerService.updateTrainer(1L, request);

        assertEquals("Updated Name", response.trainerName());
        assertEquals("trainer@example.com", response.email());

        verify(trainerRepository, never())
                .existsByEmailAndTrainerIdNot(anyString(), anyLong());
        verify(trainerRepository).save(existingTrainer);
    }

    // 9. Delete Trainer successfully
    @Test
    void shouldDeleteTrainerSuccessfully() {

        Trainer trainer = new Trainer(
                "Trainer One",
                "trainer@example.com",
                "9876543210",
                "Java",
                "MCA",
                3,
                true
        );
        trainer.setTrainerId(1L);

        when(trainerRepository.findById(1L))
                .thenReturn(Optional.of(trainer));

        trainerService.deleteTrainer(1L);

        verify(trainerRepository).findById(1L);
        verify(trainerRepository).delete(trainer);
    }

    // 10. Reject deletion when Trainer does not exist
    @Test
    void shouldThrowExceptionWhenDeletingNonExistingTrainer() {

        when(trainerRepository.findById(999L))
                .thenReturn(Optional.empty());

        TrainerNotFoundException exception = assertThrows(
                TrainerNotFoundException.class,
                () -> trainerService.deleteTrainer(999L)
        );

        assertEquals(
                "Trainer not found with ID: 999",
                exception.getMessage()
        );

        verify(trainerRepository, never()).delete(any(Trainer.class));
    }
}
