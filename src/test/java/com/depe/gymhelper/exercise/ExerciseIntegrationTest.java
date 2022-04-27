package com.depe.gymhelper.exercise;


import com.depe.gymhelper.training.TrainingDto;
import com.depe.gymhelper.training.TrainingQueryEntity;
import com.depe.gymhelper.training.TrainingService;
import com.depe.gymhelper.user.RegisterUserRequest;
import com.depe.gymhelper.user.UserQueryEntity;
import com.depe.gymhelper.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.depe.gymhelper.exercise.ExerciseType.*;
import static com.depe.gymhelper.training.TrainingStatus.DONE;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
@WithMockUser(username = "testUser", password = "testPassword")
class ExerciseIntegrationTest {

    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    private ExerciseService underTest;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private UserService userService;

    private Long initDatabaseWithTraining() {
        initDatabaseWithTestUser();
        TrainingDto trainingDto = new TrainingDto("JTCNW", DONE, LocalDateTime.now());
        return trainingService.addTraining(trainingDto);
    }

    private UserQueryEntity initDatabaseWithTestUser() {
        var registerRequest = new RegisterUserRequest(
                "email@gmail.com",
                "testUser",
                "testPassword",
                75.0,
                185L
        );
        Long id = userService.createUser(registerRequest);
        return new UserQueryEntity(id, "testUser");
    }

    @Test
    void shouldReturnListOfExercisesWithOneExercise(){
        //given
        ExerciseDto exerciseDto = new ExerciseDto(3,25.0,6,ROW);
        Long trainingId = initDatabaseWithTraining();
        var training = trainingService.createTrainingQueryEntityById(trainingId);
        //when
        underTest.addExerciseWithTraining(exerciseDto, training);
        var result = exerciseRepository.findAll();
        //then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getReps()).isEqualTo(6);
        assertThat(result.get(0).getWeight()).isEqualTo(25.0);
        assertThat(result.get(0).getType()).isEqualTo(ROW);
        assertThat(result.get(0).getSets()).isEqualTo(3);
    }

    @Test
    void shouldReturnListOfExercisesWithOneExerciseBelongsToTraining(){
        //given
        ExerciseDto exerciseDto = new ExerciseDto(3,25.0,6,ROW);
        Long trainingId = initDatabaseWithTraining();
        //when
        underTest.addExerciseWithTraining(exerciseDto, new TrainingQueryEntity(trainingId, "JTCNW"));
        var result = exerciseRepository.findAll();
        //then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getReps()).isEqualTo(6);
        assertThat(result.get(0).getWeight()).isEqualTo(25.0);
        assertThat(result.get(0).getType()).isEqualTo(ROW);
        assertThat(result.get(0).getSets()).isEqualTo(3);
        assertThat(result.get(0).getTraining().getId()).isEqualTo(trainingId);
        assertThat(result.get(0).getTraining().getDescription()).isEqualTo("JTCNW");
    }

    @Test
    void shouldReturnEmptyOptionalAfterDeleteExerciseById(){
        var exercise = initDatabaseWithExercise();
        //when
        underTest.deleteExerciseById(exercise.getId());
        //then
        assertThat(exerciseRepository.findById(exercise.getId())).isEqualTo(Optional.empty());
    }

    @Test
    void shouldUpdateExerciseByDto(){
        var exercise = initDatabaseWithExercise();
        var exerciseDto = new ExerciseDto(5,100.0, 5, BENCH_PRESS);
        underTest.updateExercise(exerciseDto, exercise.getId());
        assertThat(exercise.getWeight()).isEqualTo(100.0);
        assertThat(exercise.getSets()).isEqualTo(5);
        assertThat(exercise.getReps()).isEqualTo(5);
        assertThat(exercise.getType()).isEqualTo(BENCH_PRESS);
    }

    private Exercise initDatabaseWithExercise(){
        Long id = initDatabaseWithTraining();
        var training = new TrainingQueryEntity(id, "desc");
        var exercise = new Exercise();
        exercise.setSets(6);
        exercise.setReps(4);
        exercise.setType(OHP);
        exercise.setWeight(40.0);
        exercise.setTraining(training);
        return exerciseRepository.save(exercise);
    }

}
