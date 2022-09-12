package com.depe.gymhelper.training;

import com.depe.gymhelper.training.filter.TrainingFilter;
import com.depe.gymhelper.user.AuthenticationUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/trainings")
class TrainingController {

    private final TrainingService trainingService;
    private final TrainingQueryRepository trainingQueryRepository;
    private final AuthenticationUserService authenticationUserService;
    private final TrainingFactory trainingFactory;

    public TrainingController(
            final TrainingService trainingService,
            final TrainingQueryRepository trainingQueryRepository,
            final AuthenticationUserService authenticationUserService, TrainingFactory trainingFactory) {
        this.trainingService = trainingService;
        this.trainingQueryRepository = trainingQueryRepository;
        this.authenticationUserService = authenticationUserService;
        this.trainingFactory = trainingFactory;
    }

    @GetMapping
    ResponseEntity<List<TrainingQueryDto>> getTrainings() {
        return ResponseEntity.ok(trainingQueryRepository.findAllTrainingDtoByUser(authenticationUserService.getLoggedUser()));
    }

    @GetMapping("/{id}")
    ResponseEntity<TrainingQueryDto> getTrainingById(@PathVariable Long id) {
        return ResponseEntity.ok(
                trainingQueryRepository.findDtoByIdAndUser(
                        id,
                        authenticationUserService.getLoggedUser()).orElseThrow(() -> new TrainingNotFoundException(id)));
    }

    @GetMapping("/filter")
    ResponseEntity<List<TrainingQueryDto>> getFilteredTrainings(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) TrainingStatus status
    ) {
        TrainingFilter trainingFilter = new TrainingFilter(status, from, to);
        return ResponseEntity.ok(trainingService.getFilteredTrainings(trainingFilter));
    }

    @PostMapping
    ResponseEntity<Long> createTraining(@Valid @RequestBody TrainingDto TrainingDto) {
        return ResponseEntity.ok(trainingService.addTraining(TrainingDto));
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestParam String status) {
        trainingService.changeStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateTraining(@PathVariable Long id, @RequestBody @Valid TrainingDto trainingDto) {
        trainingService.updateTraining(id, trainingDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return ResponseEntity.noContent().build();
    }

}