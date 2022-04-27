package com.depe.gymhelper.training;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class TrainingExceptionHandler {

    @ResponseBody
    @ExceptionHandler(TrainingNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleTrainingNotFoundException(TrainingNotFoundException exception){
        return exception.getMessage();
    }

}
