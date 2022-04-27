package com.depe.gymhelper.exercise;

import com.depe.gymhelper.training.TrainingQueryEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Random;


@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseFactory exerciseFactory;

    public ExerciseService(final ExerciseRepository exerciseRepository, final ExerciseFactory exerciseFactory) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseFactory = exerciseFactory;
    }

    public Long addExerciseWithTraining(ExerciseDto exerciseDto, TrainingQueryEntity training){
        var toSave = exerciseFactory.fromDto(exerciseDto, training);
        return exerciseRepository.save(toSave).getId();
    }

    public ExerciseType[] getAllTypeOfExercises() {
        return ExerciseType.values();
    }

    @Transactional
    public void deleteExerciseById(Long id) {
        if(exerciseRepository.existsById(id)){
            exerciseRepository.deleteById(id);
        }
    }

    public void addRandomExerciseToTraining(TrainingQueryEntity training) {
        var exercise = createRandomExercise();
        exercise.setTraining(training);
        exerciseRepository.save(exercise);
    }

    @Transactional
    public void updateExercise(ExerciseDto exerciseDto, Long exerciseId) {
        var exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new ExerciseNotFoundException(exerciseId));
        exercise.updateByDto(exerciseDto);
    }

    private static Exercise  createRandomExercise(){
        var exercise =  new Exercise();
        Random random = new Random();
        ExerciseType[] exerciseTypes = ExerciseType.values();
        int number = random.nextInt(exerciseTypes.length);
        ExerciseType exerciseType = exerciseTypes[number];
        exercise.setSets(random.nextInt(5) + 1);
        exercise.setReps(random.nextInt(21) + 1);
        exercise.setWeight(random.nextDouble(20));
        exercise.setType(exerciseType);
        return exercise;
    }

}
