package com.depe.gymhelper.user;


import javax.persistence.Embeddable;

@Embeddable
class AthleteInfo {
    private Long weight;
    private Long height;

    AthleteInfo(Long height, Long weight) {
        this.weight = weight;
        this.height = height;
    }

    public AthleteInfo() {

    }

    Long getWeight() {
        return weight;
    }

    void setWeight(Long weight) {
        this.weight = weight;
    }

    Long getHeight() {
        return height;
    }

    void setHeight(Long height) {
        this.height = height;
    }
}
