package com.depe.gymhelper.exercise;

public class ExerciseDto {

    private int sets;
    private Double weight;
    private Integer reps;
    private ExerciseType type;

    public ExerciseDto(int sets, Double weight, Integer reps, ExerciseType type) {
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

    public int getSets() {
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
