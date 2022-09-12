package com.depe.gymhelper.training.exercise;


import com.depe.gymhelper.training.TrainingQueryEntity;

import javax.persistence.*;

@Entity
@Table(name = "exercises")
class Exercise {

    @Id
    @GeneratedValue
    private Long id;
    private ExerciseType type;
    private Integer sets;
    private Double weight;
    private Integer reps;
    @ManyToOne
    @JoinColumn(name = "training_id")
    private TrainingQueryEntity training;


    Long getId() {
        return id;
    }

    ExerciseType getType() {
        return type;
    }

    void setType(ExerciseType type){
        this.type = type;
    }

    Integer getSets() {
        return sets;
    }

    void setSets(Integer sets) {
        this.sets = sets;
    }

    public Double getWeight() {
        return weight;
    }

    void setWeight(Double weight) {
        this.weight = weight;
    }

    Integer getReps() {
        return reps;
    }

    void setReps(Integer reps) {
        this.reps = reps;
    }

    TrainingQueryEntity getTraining() {
        return training;
    }

    void setTraining(TrainingQueryEntity training) {
        this.training = training;
    }

    public void update(Integer reps, ExerciseType type, Double weight, Integer sets) {
        this.reps = reps;
        this.type = type;
        this.weight = weight;
        this.sets = sets;
    }
}
