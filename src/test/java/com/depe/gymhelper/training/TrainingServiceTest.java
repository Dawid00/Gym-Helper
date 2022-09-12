package com.depe.gymhelper.training;


import com.depe.gymhelper.training.filter.TrainingFilter;
import com.depe.gymhelper.user.AuthenticationUserService;
import com.depe.gymhelper.user.UserQueryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.depe.gymhelper.training.TrainingStatus.DONE;
import static com.depe.gymhelper.training.TrainingStatus.PLANNED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TrainingServiceTest {

    private TrainingRepository trainingRepository;
    private TrainingService underTest;
    private TrainingFactory trainingFactory;
    private AuthenticationUserService authenticationUserService;
    private TrainingQueryRepository trainingQueryRepository;

    @BeforeEach
    private void setUp() {
        trainingQueryRepository = mock(TrainingQueryRepository.class);
        authenticationUserService = mock(AuthenticationUserService.class);
        trainingRepository = mock(TrainingRepository.class);
        trainingFactory = mock(TrainingFactory.class);
        this.underTest = new TrainingService(trainingRepository, authenticationUserService, trainingQueryRepository, trainingFactory);
    }

    @Test
    void verifySaveMethod() {
        //given
        UserQueryEntity userQueryEntity = new UserQueryEntity(11L, "testUser");
        LocalDateTime date = LocalDateTime.now();
        TrainingDto trainingDto = new TrainingDto("JTCNW", DONE, date);
        var training = new Training(
                "JTCNW",
                DONE,
                date,
                userQueryEntity);
        training.setId(1L);
        //when
        when(authenticationUserService.getLoggedUser()).thenReturn(userQueryEntity);
        when(trainingRepository.save(training)).thenReturn(training);
        when(trainingFactory.fromDto(trainingDto, userQueryEntity)).thenReturn(training);
        underTest.addTraining(trainingDto);
        //then
        verify(trainingRepository, times(1)).save(training);
    }

    @Test
    void verifyDeleteMethod() {
        //when
        underTest.deleteTraining(10L);
        //then
        verify(trainingRepository, times(1)).deleteById(10L);
    }

    @Test
    void shouldChangeStatus() {
        //given
        UserQueryEntity userQueryEntity = new UserQueryEntity(11L, "testUser");
        LocalDateTime date = LocalDateTime.now();
        var training = new Training(
                "JTCNW",
                PLANNED,
                date,
                userQueryEntity);
        training.setId(10L);
        //when
        when(trainingRepository.findById(10L)).thenReturn(Optional.of(training));
        underTest.changeStatus(10L, "planned");
        //then
        assertThat(training.getStatus()).isEqualTo(PLANNED);
        assertThat(training.getId()).isEqualTo(10L);
    }

    @Test
    void shouldThrowTrainingNotFoundException() {
        assertThrows(TrainingNotFoundException.class, () -> underTest.changeStatus(10L, "planned"));
    }

    @Test
    void shouldThrowIllegalArgumentException() {
        var training = new Training();
        when(trainingRepository.findById(10L)).thenReturn(Optional.of(training));
        assertThrows(IllegalArgumentException.class, () -> underTest.changeStatus(10L, "not a status"));
    }


    @Test
    void shouldUpdateTraining() {
        //given
        UserQueryEntity userQueryEntity = new UserQueryEntity(11L, "testUser");
        LocalDateTime date = LocalDateTime.now();
        TrainingDto trainingDto = new TrainingDto("JTCNW", DONE, date);
        var training = new Training(
                "JTCNW",
                DONE,
                date,
                userQueryEntity);
        training.setId(1L);
        //when
        when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));
        underTest.updateTraining(1L, trainingDto);
        //then
        assertThat(training.getStatus()).isEqualTo(DONE);
        assertThat(training.getId()).isEqualTo(1L);
    }

    @Test
    void shouldReturnTrainingQueryEntity() {
        //given
        UserQueryEntity userQueryEntity = new UserQueryEntity(11L, "testUser");
        LocalDateTime date = LocalDateTime.now();
        var training = new Training(
                "Never give up",
                DONE,
                date,
                userQueryEntity);
        training.setId(1L);
        //when
        when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));
        when(
                trainingFactory
                        .createTrainingQueryEntityById(1L, "Never give up"))
                .thenReturn(new TrainingQueryEntity(1L, "Never give up")
                );
        var result = underTest.getTrainingQueryEntityById(1L);
        //then
        assertThat(result.getDescription()).isEqualTo("Never give up");
        assertThat(result.getId()).isEqualTo(1L);
    }

    @ParameterizedTest
    @MethodSource("provideFiltersForTraining")
    void shouldReturnListOfFilteredTrainingQueryDto(String from, String to, TrainingStatus trainingStatus, int size) {
        //given
        UserQueryEntity userQueryEntity = new UserQueryEntity(11L, "testUser");
        LocalDateTime date = LocalDateTime.parse("2022-04-09T00:00:00.00000");
        var training = new TrainingQueryDto(11L, "Never give up", trainingStatus, date);
        List<TrainingQueryDto> trainings = new ArrayList<>();
        trainings.add(training);
        //when
        when(authenticationUserService.getLoggedUser()).thenReturn(userQueryEntity);
        when(trainingQueryRepository.findAllDtoBetweenDateByStatusAndUser(LocalDateTime.parse(from), LocalDateTime.parse(to), trainingStatus, userQueryEntity)).thenReturn(trainings);
        var result = underTest.getFilteredTrainings(
                new TrainingFilter(
                        trainingStatus,
                        LocalDateTime.parse(from),
                        LocalDateTime.parse(to)
                )
        );
        //then
        assertThat(result).hasSize(size);
    }

    private static Stream<Arguments> provideFiltersForTraining() {
        return Stream.of(
                Arguments.of("2022-04-09T00:00:00.000", "2022-04-11T00:00:00.000", DONE, 1),
                Arguments.of("2022-04-09T00:00:00.000", "2022-04-11T00:00:00.000", PLANNED, 1),
                Arguments.of("2022-04-01T00:00:00.000", "2022-04-30T00:00:00.000", DONE, 1),
                Arguments.of("2022-04-01T00:00:00.000", "2022-04-30T00:00:00.000", PLANNED, 1),
                Arguments.of("2021-04-01T00:00:00.000", "2021-04-30T00:00:00.000", PLANNED, 1)
        );
    }
}
