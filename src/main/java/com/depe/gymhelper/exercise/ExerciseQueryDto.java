package com.depe.gymhelper.exercise;

public interface ExerciseQueryDto {
    Long getId();
    Integer getSets();
    Double getWeight();
    Integer getReps();
    Long getTrainingId();
    ExerciseTypeName getType();
}
