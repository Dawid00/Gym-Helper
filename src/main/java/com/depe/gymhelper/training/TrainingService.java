package com.depe.gymhelper.training;

import com.depe.gymhelper.training.filter.TrainingFilter;
import com.depe.gymhelper.user.AuthenticationUserService;
import com.depe.gymhelper.user.UserQueryEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final AuthenticationUserService authenticationUserService;
    private final TrainingQueryRepository trainingQueryRepository;
    private final TrainingFactory trainingFactory;

    public TrainingService(final TrainingRepository trainingRepository, final AuthenticationUserService authenticationUserService, final TrainingQueryRepository trainingQueryRepository, final TrainingFactory trainingFactory) {
        this.trainingRepository = trainingRepository;
        this.authenticationUserService = authenticationUserService;
        this.trainingQueryRepository = trainingQueryRepository;
        this.trainingFactory = trainingFactory;
    }

    public Long addTraining(TrainingDto trainingDto) {
        var user = authenticationUserService.getLoggedUser();
        var toSave = trainingFactory.fromDto(trainingDto, user);
        return trainingRepository.save(toSave).getId();
    }


    @Transactional
    public void changeStatus(Long id, String status) {
        Training training = getTrainingById(id);
        training.changeStatus(status);
    }

    @Transactional
    public void updateTraining(Long trainingId, TrainingDto trainingDto) {
        var training = getTrainingById(trainingId);
        training.update(trainingDto.getDescription(), trainingDto.getDate(), trainingDto.getStatus());
    }

    public TrainingQueryEntity getTrainingQueryEntityById(Long trainingId) {
        var training = getTrainingById(trainingId);
        return trainingFactory.createTrainingQueryEntityById(training.getId(), training.getDescription());
    }

    @Transactional

    public void deleteTraining(Long trainingId) {
        trainingRepository.deleteById(trainingId);
    }

    public List<TrainingQueryDto> getFilteredTrainings(TrainingFilter filter) {
        var user = authenticationUserService.getLoggedUser();
        return getFilteredTrainingsByUser(user, filter);
    }

    private List<TrainingQueryDto> getFilteredTrainingsByUser(UserQueryEntity user, TrainingFilter filter) {
        return switch (filter.getFilterType()) {
            case STATUS -> getTrainingsByStatus(filter.getStatus(), user);
            case STATUS_DATE -> getTrainingsByStatusBetweenDates(
                    filter.getFrom(),
                    filter.getTo(),
                    filter.getStatus(),
                    user);
            case DATE -> getTrainingsBetweenDates(filter.getFrom(), filter.getTo(), user);
            default -> Collections.emptyList();
        };
    }

    private Training getTrainingById(Long id) {
        return trainingRepository.findById(id).orElseThrow(() -> new TrainingNotFoundException(id));
    }

    private List<TrainingQueryDto> getTrainingsBetweenDates(LocalDateTime from, LocalDateTime to, UserQueryEntity user) {
        return trainingQueryRepository.findAllDtoBetweenDateByUser(from, to, user);
    }

    private List<TrainingQueryDto> getTrainingsByStatus(TrainingStatus status, UserQueryEntity user) {
        return trainingQueryRepository.findAllDtoByStatusAndUser(status, user);
    }

    private List<TrainingQueryDto> getTrainingsByStatusBetweenDates(LocalDateTime from, LocalDateTime to, TrainingStatus status, UserQueryEntity user) {
        return trainingQueryRepository.findAllDtoBetweenDateByStatusAndUser(from, to, status, user);
    }
}