package com.depe.gymhelper.exercise;

public class ExerciseDto {

    private Integer sets;
    private Double weight;
    private Integer reps;
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
