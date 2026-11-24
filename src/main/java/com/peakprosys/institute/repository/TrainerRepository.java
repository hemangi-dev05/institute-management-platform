
package com.peakprosys.institute.repository;

import com.peakprosys.institute.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndTrainerIdNot(String email, Long trainerId);
}
