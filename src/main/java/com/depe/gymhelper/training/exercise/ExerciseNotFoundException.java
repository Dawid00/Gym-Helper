package com.depe.gymhelper.training.exercise;

class ExerciseNotFoundException extends RuntimeException {
    ExerciseNotFoundException(Long exerciseId) {
        super("Exercise with id: " + exerciseId + "not found");
    }
}
