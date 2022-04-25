package com.depe.gymhelper.training;

enum TrainingStatus {
    DONE("DONE"), PLANNED("PLANNED");

    final String status;
    TrainingStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
