package com.depe.gymhelper.exercise;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ExerciseDto {

    @NotNull
    @Min(value = 1, message = "Exercise must have minimum 1 series")
    @Max(value = 100,  message = "Exercise must have maximum 100 series")
    private Integer sets;
    @NotNull
    @Min(value = 0, message = "Exercise must have minimum 0 kg")
    @Max(value = 500,  message = "Exercise must have maximum 500 kg")
    private Double weight;
    @NotNull
    @Min(value = 1, message = "Exercise must have minimum 1 repetition")
    @Max(value = 100,  message = "Exercise must have maximum 100 repetition")
    private Integer reps;
    @NotNull(message = "Exercise must have type")
    private ExerciseType type;

    public ExerciseDto(Integer sets, Double weight, Integer reps, ExerciseType type) {
        this.sets = sets;
        this.weight = weight;
        this.reps = reps;
        this.type = type;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public ExerciseType getType() {
        return type;
    }

    public void setType(ExerciseType type) {
        this.type = type;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

}
