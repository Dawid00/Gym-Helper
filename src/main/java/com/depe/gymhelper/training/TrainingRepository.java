package com.depe.gymhelper.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
interface TrainingRepository extends JpaRepository<Training, Long> {
    Optional<Training> findById(Long id);
    void deleteById(Long trainingId);
}