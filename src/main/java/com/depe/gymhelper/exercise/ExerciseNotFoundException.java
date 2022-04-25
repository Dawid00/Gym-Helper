package com.depe.gymhelper.exercise;

class ExerciseNotFoundException extends RuntimeException {
    ExerciseNotFoundException(Long exerciseId) {
        super("Exercise with id: " + exerciseId + "not found");
    }
}
