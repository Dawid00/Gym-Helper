package com.depe.gymhelper.training;

import com.depe.gymhelper.user.AuthenticationUserService;
import com.depe.gymhelper.user.UserQueryEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    public Long addTraining(TrainingDto trainingDto){
        var user  = authenticationUserService.getLoggedUser();
        var toSave = trainingFactory.fromDto(trainingDto, user);
        return trainingRepository.save(toSave).getId();
    }

    public TrainingQueryEntity createTrainingQueryEntityById(Long trainingId) {
        var training = getTraining(trainingId);
        return new TrainingQueryEntity(training.getId(), training.getDescription());
    }

    @Transactional
    public void changeStatus(Long id, String status) {
        Training training = getTraining(id);
        switch(status.toUpperCase(Locale.ROOT)){
            case "PLANNED" -> training.setStatus(TrainingStatus.PLANNED);
            case "DONE" -> training.setStatus(TrainingStatus.DONE);
        }
    }

    private Training getTraining(Long id) {
        return trainingRepository.findById(id).orElseThrow(()-> new TrainingNotFoundException(id));
    }

    @Transactional
    public void updateTraining(Long trainingId, TrainingDto trainingDto) {
        var training = getTraining(trainingId);
        trainingFactory.updateFromDto(trainingDto, training);
    }

    @Transactional
    public void deleteTraining(Long trainingId) {
        trainingRepository.deleteById(trainingId);
    }

    public List<TrainingQueryDto> getFilteredTrainings(Map<String, String> filter) {
        final String DATE_SUFFIX = "T00:00:00.00000";
        var user = authenticationUserService.getLoggedUser();
        if (isStatusFilter(filter) && isDateFilter(filter)) {
            LocalDateTime from = LocalDateTime.parse(filter.get("from") + DATE_SUFFIX);
            LocalDateTime to = LocalDateTime.parse(filter.get("to") + DATE_SUFFIX);
            TrainingStatus status = TrainingStatus.valueOf(filter.get("status").toUpperCase(Locale.ROOT));
            return getTrainingsByStatusBetweenDates(
                    from,
                    to,
                    status,
                    user);
        }
        if (isStatusFilter(filter)) {
            return getTrainingsByStatus(TrainingStatus.valueOf(filter.get("status").toUpperCase(Locale.ROOT)), user);
        } else if (isDateFilter(filter)) {
            LocalDateTime from = LocalDateTime.parse(filter.get("from") + DATE_SUFFIX);
            LocalDateTime to = LocalDateTime.parse(filter.get("to") + DATE_SUFFIX);
            return getTrainingsBetweenDates(from, to, user);
        }
        return Collections.emptyList();}

    private List<TrainingQueryDto> getTrainingsBetweenDates(LocalDateTime from, LocalDateTime to, UserQueryEntity user) {
        return trainingQueryRepository.findAllDtoBetweenDateByUser(from, to, user);
    }

    private List<TrainingQueryDto> getTrainingsByStatus(TrainingStatus status, UserQueryEntity user) {
        return trainingQueryRepository.findAllDtoByStatusAndUser(status, user);
    }

    private List<TrainingQueryDto> getTrainingsByStatusBetweenDates(LocalDateTime from, LocalDateTime to, TrainingStatus status, UserQueryEntity user) {
        return trainingQueryRepository.findAllDtoBetweenDateByStatusAndUser(from, to, status, user);
    }

    private boolean isDateFilter(Map<String, String> filter) {
        return filter.containsKey("from") && filter.containsKey("to");
    }

    private boolean isStatusFilter(Map<String, String> filter) {
        return filter.containsKey("status");
    }
}

