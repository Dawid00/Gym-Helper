package com.depe.gymhelper.exercise;

import com.depe.gymhelper.training.TrainingQueryEntity;
import org.springframework.stereotype.Service;

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

}
