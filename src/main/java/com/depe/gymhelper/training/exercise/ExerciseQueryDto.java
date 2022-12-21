package com.depe.gymhelper.training.exercise;

public interface ExerciseQueryDto {
    Long getId();
    Integer getSets();
    Double getWeight();
    Integer getReps();
    Long getTrainingId();
    String getType();
}
