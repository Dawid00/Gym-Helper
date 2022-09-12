package com.depe.gymhelper.training.exercise;


import org.springframework.data.repository.Repository;
import java.util.List;
import java.util.Optional;

public interface ExerciseQueryRepository extends Repository<Exercise, Long> {

    List<ExerciseQueryDto> findAllDtoByTrainingId(Long trainingId);
    Optional<ExerciseQueryDto> findDtoById(Long id);
}

