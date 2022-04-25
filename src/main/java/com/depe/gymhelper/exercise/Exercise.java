package com.depe.gymhelper.exercise;


import com.depe.gymhelper.training.TrainingQueryEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "exercises")
class Exercise {

    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
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
    void setId(Long id) {
        this.id = id;
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

    public void updateByDto(ExerciseDto exerciseDto) {
        this.weight = exerciseDto.getWeight();
        this.reps = exerciseDto.getReps();
        this.type = exerciseDto.getType();
        this.sets = exerciseDto.getSets();
    }
}
