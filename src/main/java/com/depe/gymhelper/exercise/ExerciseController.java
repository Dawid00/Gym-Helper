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
import java.util.List;

@RestController
@RequestMapping("/api")
class ExerciseController {

    private final ExerciseService exerciseService;
    private final TrainingService trainingService;
    private final ExerciseQueryRepository exerciseQueryRepository;

    ExerciseController(final ExerciseService exerciseService, final TrainingService trainingService, final ExerciseQueryRepository exerciseQueryRepository) {
        this.exerciseService = exerciseService;
        this.trainingService = trainingService;
        this.exerciseQueryRepository = exerciseQueryRepository;
    }

    @GetMapping("/exercises/all-type")
    ResponseEntity<ExerciseType[]> getAllTypeOfExercises(){
        return ResponseEntity.ok(exerciseService.getAllTypeOfExercises());
    }

    @GetMapping("/trainings/{trainingId}/exercises")
    ResponseEntity<List<ExerciseQueryDto>> getExercisesFromTraining(@PathVariable Long trainingId) {
        return ResponseEntity.ok(exerciseQueryRepository.findAllDtoByTrainingId(trainingId));
    }

    @GetMapping("/exercises/{id}")
    ResponseEntity<ExerciseQueryDto> getExerciseById(@PathVariable Long id){
        return ResponseEntity.ok(exerciseQueryRepository.findDtoById(id).orElseThrow(RuntimeException::new));
    }

    @PostMapping("/trainings/{trainingId}/exercises")
    ResponseEntity<Long> createExerciseWithTraining(@RequestBody ExerciseDto exerciseDto, @PathVariable Long trainingId){
        var training = trainingService.createTrainingQueryEntityById(trainingId);
        return ResponseEntity.ok(exerciseService.addExerciseWithTraining(exerciseDto, training));
    }

    @PostMapping("/trainings/{trainingId}/exercises/random")
    ResponseEntity<?> addRandomExerciseToTrainingWithId(@PathVariable Long trainingId){
        var training = trainingService.createTrainingQueryEntityById(trainingId);
        exerciseService.addRandomExerciseToTraining(training);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/exercises/{id}")
    ResponseEntity<?> updateExerciseById( @PathVariable Long id, @Valid @RequestBody ExerciseDto exerciseDto){
        exerciseService.updateExercise(exerciseDto, id);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/exercises/{id}")
    ResponseEntity<?> deleteExercise(Long id) {
        exerciseService.deleteExerciseById(id);
        return ResponseEntity.noContent().build();
    }
}