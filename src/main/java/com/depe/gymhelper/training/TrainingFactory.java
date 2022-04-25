package com.depe.gymhelper.training;

import com.depe.gymhelper.user.UserQueryEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
class TrainingFactory {

    Training fromDto(TrainingDto TrainingDto, UserQueryEntity userQueryEntity){
        var training = new Training();
        training.setDescription(TrainingDto.getDescription());
        training.setStatus(TrainingDto.getStatus());
        training.setDate(TrainingDto.getDate());
        if(training.getDate() == null){
            training.setDate(LocalDateTime.now());
        }
        training.setUser(userQueryEntity);
        return training;
    }


    public void updateFromDto(TrainingDto trainingDto, Training training) {
        training.setStatus(trainingDto.getStatus());
        training.setDate(trainingDto.getDate());
        training.setDescription(trainingDto.getDescription());
    }
}