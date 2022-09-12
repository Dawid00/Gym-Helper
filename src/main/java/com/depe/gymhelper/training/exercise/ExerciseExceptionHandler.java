package com.depe.gymhelper.training.exercise;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ExerciseExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ExerciseNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleTrainingNotFoundException(ExerciseNotFoundException exception){
        return exception.getMessage();
    }
}
