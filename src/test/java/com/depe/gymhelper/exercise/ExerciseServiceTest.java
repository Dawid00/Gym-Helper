package com.depe.gymhelper.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.depe.gymhelper.exercise.ExerciseType.ROW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ExerciseServiceTest {

    private ExerciseFactory exerciseFactory;
    private ExerciseRepository exerciseRepository;
    private ExerciseService underTest;

    @BeforeEach
    private void setUp(){
        exerciseRepository = mock(ExerciseRepository.class);
        exerciseFactory = mock(ExerciseFactory.class);
        this.underTest = new ExerciseService(exerciseRepository, exerciseFactory);
    }

    @Test
    void verifySaveMethod(){
        //given
        Exercise exercise = new Exercise();
        exercise.setType(ROW);
        exercise.setWeight(25.0);
        exercise.setReps(5);
        exercise.setSets(3);
        ExerciseDto exerciseDto = new ExerciseDto(3,25.0,5, ROW);
        //when
        when(exerciseFactory.fromDto(exerciseDto, null)).thenReturn(exercise);
        when(exerciseRepository.save(exercise)).thenReturn(exercise);
        underTest.addExercise(exerciseDto);
        //then
        verify(exerciseRepository, times(1)).save(exercise);
    }

    @Test
    void verifyDeleteMethod(){
        //given
        Exercise exercise = new Exercise();
        exercise.setType(ROW);
        exercise.setWeight(25.0);
        exercise.setReps(5);
        exercise.setSets(3);
        ExerciseDto exerciseDto = new ExerciseDto(3,25.0,5, ROW);
        //when
        when(exerciseRepository.existsById(1L)).thenReturn(true);
        underTest.deleteExerciseById(1L);
        //then
        verify(exerciseRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldUpdateExercise(){
        //given
        Exercise exercise = new Exercise();
        exercise.setType(ROW);
        exercise.setWeight(25.0);
        exercise.setReps(5);
        exercise.setSets(3);
        ExerciseDto exerciseDto = new ExerciseDto(3,25.0,5, ROW);
        //when
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        underTest.updateExercise(exerciseDto, 1L);
        //then
        assertThat(exercise.getReps()).isEqualTo(5);
        assertThat(exercise.getSets()).isEqualTo(3);
        assertThat(exercise.getWeight()).isEqualTo(25.0);
        assertThat(exercise.getType()).isEqualTo(ROW);
    }

    @Test
    void shouldThrowExerciseNotFoundException(){
        //when
        when(exerciseRepository.existsById(1L)).thenReturn(false);
        assertThrows(ExerciseNotFoundException.class, () -> underTest.updateExercise(new ExerciseDto(2,25.0,3,ROW), 1L));
    }



}
