package com.depe.gymhelper.training.exercise;

enum ExerciseType {
    PULL_UP("pull up", "back"),
    DEAD_LIFT("dead lift", "back"),
    ROW("row", "back"),
    PUSH_UP("push up", "chest"),
    BENCH_PRESS("bench press"," chest"),
    OHP("ohp", "shoulders"),
    FACE_PULL("face pull", "shoulders"),
    T_RAISE("t raise", "shoulders"),
    ABS_WHEEL("abs wheel", "abs"),
    BICEPS("biceps", "biceps"),
    DIPS("dips", "triceps"),
    SQUAT("squat", "leg"),
    FRONT_SQUAT("front squat", "leg");

    private final String name;
    private final String bodyPart;

    ExerciseType(String name, String bodyPart) {
        this.name = name;
        this.bodyPart = bodyPart;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public String getName() {
        return name;
    }
}
