package com.depe.gymhelper.training;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trainings")
public class TrainingQueryEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    public TrainingQueryEntity() {
    }

    public TrainingQueryEntity(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
