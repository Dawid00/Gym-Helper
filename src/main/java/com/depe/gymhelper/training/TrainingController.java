package com.depe.gymhelper.training;

import com.depe.gymhelper.user.AuthenticationUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trainings")
class TrainingController {

    private final TrainingService trainingService;
    private final TrainingQueryRepository trainingQueryRepository;
    private final AuthenticationUserService authenticationUserService;

    public TrainingController(
            final TrainingService trainingService,
            final TrainingQueryRepository trainingQueryRepository,
            final AuthenticationUserService authenticationUserService) {
        this.trainingService = trainingService;
        this.trainingQueryRepository = trainingQueryRepository;
        this.authenticationUserService = authenticationUserService;
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
    ResponseEntity<List<TrainingQueryDto>> getFilteredTrainings(@RequestParam Map<String, String> filter) {
        return ResponseEntity.ok(trainingService.getFilteredTrainings(filter));
    }

    @PostMapping
    ResponseEntity<Long> createTraining(@RequestBody @Valid TrainingDto TrainingDto) {
        return ResponseEntity.ok(trainingService.addTraining(TrainingDto));
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestParam String status) {
        trainingService.changeStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateTraining(@PathVariable Long id, @RequestBody @Valid TrainingDto trainingDto){
        trainingService.updateTraining(id,trainingDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTraining(@PathVariable Long id){
        trainingService.deleteTraining(id);
        return ResponseEntity.noContent().build();
    }

}