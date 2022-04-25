package com.depe.gymhelper.training;

import com.depe.gymhelper.user.UserQueryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TrainingQueryRepository extends Repository<Training, Long> {

    Optional<TrainingQueryDto> findDtoByIdAndUser(Long id, UserQueryEntity user);
    List<TrainingQueryDto> findAllTrainingDtoByUser(UserQueryEntity user);
    List<TrainingQueryDto> findAllDtoByStatusAndUser(TrainingStatus status, UserQueryEntity user);
    @Query("SELECT new com.depe.gymhelper.training.TrainingQueryDto(t.id, t.description, t.status, t.date) from Training t WHERE t.date > :from AND t.date < :to AND t.user = :user")
    List<TrainingQueryDto> findAllDtoBetweenDateByUser(LocalDateTime from, LocalDateTime to, UserQueryEntity user);
    @Query("SELECT new com.depe.gymhelper.training.TrainingQueryDto(t.id, t.description, t.status, t.date) from Training t WHERE t.date > :from AND t.date < :to AND t.status = :status AND t.user = :user")
    List<TrainingQueryDto> findAllDtoBetweenDateByStatusAndUser(LocalDateTime from, LocalDateTime to, TrainingStatus status, UserQueryEntity user);
}