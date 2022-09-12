package com.depe.gymhelper.training.exercise;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ExerciseDto {

    @NotNull
    @Min(value = 1, message = "Exercise must have minimum 1 series")
    @Max(value = 100, message = "Exercise must have maximum 100 series")
    private final Integer sets;
    @NotNull
    @Min(value = 0, message = "Exercise must have minimum 0 kg")
    @Max(value = 500, message = "Exercise must have maximum 500 kg")
    private final Double weight;
    @NotNull
    @Min(value = 1, message = "Exercise must have minimum 1 repetition")
    @Max(value = 100, message = "Exercise must have maximum 100 repetition")
    private final Integer reps;
    @NotNull(message = "Exercise must have type")
    private final ExerciseType type;

    public ExerciseDto(Integer sets, Double weight, Integer reps, ExerciseType type) {
        this.sets = sets;
        this.weight = weight;
        this.reps = reps;
        this.type = type;
    }

    public ExerciseType getType() {
        return type;
    }

    public Integer getSets() {
        return sets;
    }

    public Double getWeight() {
        return weight;
    }

    public Integer getReps() {
        return reps;
    }
}
