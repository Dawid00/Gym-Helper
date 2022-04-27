package com.depe.gymhelper.user;


import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Embeddable
class AthleteInfo {

    private @NotNull() @Min(value = 1, message = "Its not possible") @Max(value = 1000, message = "Its not possible") Double weight;
    @NotNull
    @Min(value = 1, message = "Its not possible")
    @Max(value = 250,  message = "Its not possible")
    private Long height;

    AthleteInfo(Long height, @NotNull() @Min(value = 1, message = "Its not possible") @Max(value = 1000, message = "Its not possible") Double weight) {
        this.weight = weight;
        this.height = height;
    }

    public AthleteInfo() {

    }

    @NotNull() @Min(value = 1, message = "Its not possible") @Max(value = 1000, message = "Its not possible") Double getWeight() {
        return weight;
    }

    void setWeight(@NotNull() @Min(value = 1, message = "Its not possible") @Max(value = 1000, message = "Its not possible") Double weight) {
        this.weight = weight;
    }

    Long getHeight() {
        return height;
    }

    void setHeight(Long height) {
        this.height = height;
    }
}
