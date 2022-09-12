package com.depe.gymhelper.training;

import com.depe.gymhelper.user.UserQueryEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
class TrainingFactory {

    Training fromDto(TrainingDto trainingDto, UserQueryEntity userQueryEntity) {
        return new Training(
                trainingDto.getDescription(),
                trainingDto.getStatus(),
                trainingDto.getDate() == null ? LocalDateTime.now(): trainingDto.getDate(),
                userQueryEntity);
    }

    public TrainingQueryEntity createTrainingQueryEntityById(Long trainingId, String description) {
        return new TrainingQueryEntity(trainingId, description);
    }
}