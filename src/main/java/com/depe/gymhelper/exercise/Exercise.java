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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "exercises")
class Exercise {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull(message = "Exercise must have type")
    @Enumerated(EnumType.STRING)
    private ExerciseType type;
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
