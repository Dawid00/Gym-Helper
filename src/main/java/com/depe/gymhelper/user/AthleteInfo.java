package com.depe.gymhelper.user;


import javax.persistence.Embeddable;

@Embeddable
class AthleteInfo {
    private Long weight;
    private Long height;
    private Integer age;

    AthleteInfo(Long height, Integer age, Long weight) {
        this.weight = weight;
        this.height = height;
        this.age = age;
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

    Integer getAge() {
        return age;
    }

    void setAge(Integer age) {
        this.age = age;
    }
}
