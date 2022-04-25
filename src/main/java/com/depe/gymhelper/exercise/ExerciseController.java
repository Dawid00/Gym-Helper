package com.depe.gymhelper.exercise;


import com.depe.gymhelper.training.TrainingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/exercises")
class ExerciseController {

    private final ExerciseService exerciseService;
    private final TrainingService trainingService;
    private final ExerciseQueryRepository exerciseQueryRepository;

    ExerciseController(final ExerciseService exerciseService, final TrainingService trainingService, final ExerciseQueryRepository exerciseQueryRepository) {
        this.exerciseService = exerciseService;
        this.trainingService = trainingService;
        this.exerciseQueryRepository = exerciseQueryRepository;
    }

    @PostMapping("/{trainingId}")
    ResponseEntity<Long> createExerciseWithTraining(@RequestBody ExerciseDto exerciseDto, @PathVariable Long trainingId){
        var training = trainingService.createTrainingQueryEntityById(trainingId);
        return ResponseEntity.ok(exerciseService.addExerciseWithTraining(exerciseDto, training));
    }

    @GetMapping("/{id}")
    ResponseEntity<ExerciseQueryDto> getExerciseById(@PathVariable Long id){
        return ResponseEntity.ok(exerciseQueryRepository.findDtoById(id).orElseThrow(RuntimeException::new));
    }

    @GetMapping("/all-type")
    ResponseEntity<ExerciseType[]> getAllTypeOfExercises(){
        return ResponseEntity.ok(exerciseService.getAllTypeOfExercises());
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> deleteExercise(Long id) {
        exerciseService.deleteExerciseById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/random/{trainingId}")
    ResponseEntity<?> addRandomExerciseToTrainingWithId(@PathVariable Long trainingId){
        var training = trainingService.createTrainingQueryEntityById(trainingId);
        exerciseService.addRandomExerciseToTraining(training);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/trainings/{trainingId}/exercises/{exerciseId}")
    ResponseEntity<?> updateExerciseByIdBelongsToTrainingWithId(@PathVariable Long trainingId, @PathVariable Long exerciseId, @Valid @RequestBody ExerciseDto exerciseDto){
        exerciseService.updateExercise(exerciseDto, exerciseId);
        return ResponseEntity.status(204).build();
    }
}