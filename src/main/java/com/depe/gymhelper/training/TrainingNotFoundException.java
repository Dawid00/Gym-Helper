package com.depe.gymhelper.training;

class TrainingNotFoundException extends RuntimeException {
    public TrainingNotFoundException(Long trainingId) {
        super("Training with %s id not found".formatted(trainingId));
    }
}
