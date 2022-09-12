package com.depe.gymhelper.training.exercise;

import com.depe.gymhelper.training.TrainingQueryEntity;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
class ExerciseFactory {

    Exercise fromDto(ExerciseDto exerciseDto, TrainingQueryEntity trainingQueryEntity){
        var exercise = new Exercise();
        exercise.setReps(exerciseDto.getReps());
        exercise.setSets(exerciseDto.getSets());
        exercise.setType(exerciseDto.getType());
        exercise.setWeight(exerciseDto.getWeight());
        exercise.setTraining(trainingQueryEntity);
        return exercise;
    }

    Exercise createRandomExercise(TrainingQueryEntity trainingQueryEntity) {
        var exercise = new Exercise();
        Random random = new Random();
        ExerciseType[] exerciseTypes = ExerciseType.values();
        int number = random.nextInt(exerciseTypes.length);
        ExerciseType exerciseType = exerciseTypes[number];
        exercise.setSets(random.nextInt(5) + 1);
        exercise.setReps(random.nextInt(21) + 1);
        exercise.setWeight(random.nextDouble(20));
        exercise.setType(exerciseType);
        exercise.setTraining(trainingQueryEntity);
        return exercise;
    }
}

