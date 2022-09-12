package com.depe.gymhelper.training.exercise;

import com.depe.gymhelper.training.TrainingQueryEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseFactory exerciseFactory;

    public ExerciseService(final ExerciseRepository exerciseRepository, final ExerciseFactory exerciseFactory) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseFactory = exerciseFactory;
    }

    public Long addExerciseWithTraining(ExerciseDto exerciseDto, TrainingQueryEntity training) {
        var toSave = exerciseFactory.fromDto(exerciseDto, training);
        return exerciseRepository.save(toSave).getId();
    }

    public ExerciseType[] getAllTypeOfExercises() {
        return ExerciseType.values();
    }

    @Transactional
    public void deleteExerciseById(Long id) {
        if (exerciseRepository.existsById(id)) {
            exerciseRepository.deleteById(id);
        }
    }

    public void addRandomExerciseToTraining(TrainingQueryEntity training) {
        var exercise = exerciseFactory.createRandomExercise(training);
        exerciseRepository.save(exercise);
    }

    @Transactional
    public void updateExercise(ExerciseDto exerciseDto, Long exerciseId) {
        var exercise = exerciseRepository
                .findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException(exerciseId));
        exercise.update(
                exerciseDto.getReps(),
                exerciseDto.getType(),
                exerciseDto.getWeight(),
                exerciseDto.getSets()
        );
    }


}
