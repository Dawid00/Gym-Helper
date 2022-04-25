package com.depe.gymhelper.training;


import com.depe.gymhelper.user.AuthenticationUserService;
import com.depe.gymhelper.user.RegisterUserRequest;

import com.depe.gymhelper.user.UserQueryEntity;
import com.depe.gymhelper.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.depe.gymhelper.training.TrainingStatus.DONE;
import static com.depe.gymhelper.training.TrainingStatus.PLANNED;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@WithMockUser(username = "testUser", password = "testPassword")
class TrainingIntegrationTest {


    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private TrainingFactory trainingFactory;
    @Autowired
    private TrainingService underTest;
    @Autowired
    private UserService userService;

    private Training initDatabaseWithTraining() {
        var user = initDatabaseWithTestUser();
        TrainingDto trainingDto = new TrainingDto("JTCNW", DONE, LocalDateTime.now());
        var training = trainingFactory.fromDto(trainingDto, user);
        return trainingRepository.save(training);
    }

    private void initDatabaseWithTrainings() {
        var user = initDatabaseWithTestUser();
        LocalDateTime now = LocalDateTime.parse("2022-04-10T12:00:00.00000");
        TrainingDto trainingDto = new TrainingDto("JTCNW", DONE, now);
        TrainingDto trainingDto2 = new TrainingDto("a", DONE, now.minusDays(4));
        TrainingDto trainingDto3 = new TrainingDto("b", PLANNED, now.plusDays(3));
        TrainingDto trainingDto4 = new TrainingDto("c", DONE, now.plusDays(100));
        TrainingDto trainingDto5 = new TrainingDto("d", PLANNED, now.plusDays(100));
        trainingRepository.save(trainingFactory.fromDto(trainingDto, user));
        trainingRepository.save(trainingFactory.fromDto(trainingDto2, user));
        trainingRepository.save(trainingFactory.fromDto(trainingDto3, user));
        trainingRepository.save(trainingFactory.fromDto(trainingDto4, user));
        trainingRepository.save(trainingFactory.fromDto(trainingDto5, user));
    }

    private UserQueryEntity initDatabaseWithTestUser() {
        var registerRequest = new RegisterUserRequest(
                "email@gmail.com",
                "testUser",
                "testPassword",
                85L,
                185L,
                22
        );
        Long id = userService.createUser(registerRequest);
        return new UserQueryEntity(id, "testUser");
    }


    @Test
    void shouldReturnTrainingListWithOneElements() {
        //given
        initDatabaseWithTestUser();
        TrainingDto trainingDto = new TrainingDto("JTCNW", DONE, LocalDateTime.now());
        //when
        underTest.addTraining(trainingDto);
        //then
        assertThat(trainingRepository.findAll()).hasSize(1);
    }

    @Test
    void shouldReturnTrainingListWithOneElement() {
        //given
        var user = initDatabaseWithTestUser();
        TrainingDto trainingDto = new TrainingDto("JTCNW", DONE, LocalDateTime.now());
        var training = trainingFactory.fromDto(trainingDto, user);
        Long id = trainingRepository.save(training).getId();

        //when
        var result = underTest.createTrainingQueryEntityById(id);
        //then
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getDescription()).isEqualTo("JTCNW");
    }


    @ParameterizedTest()
    @MethodSource("provideTrainingStatus")
    void shouldChangeStatus(TrainingStatus status, String newStatus) {
        //given
        var user = initDatabaseWithTestUser();
        TrainingDto trainingDto = new TrainingDto("JTCNW", status, LocalDateTime.now());
        var training = trainingFactory.fromDto(trainingDto, user);
        Long id = trainingRepository.save(training).getId();
        TrainingStatus newTrainingStatus = TrainingStatus.valueOf(newStatus.toUpperCase(Locale.ROOT));
        //when
        underTest.changeStatus(id, newStatus);
        //then
        assertThat(training.getStatus()).isEqualTo(newTrainingStatus);
    }

    @Test
    void shouldReturnEmptyOfTrainingAfterDeleteTraining() {
        //given
        var user = initDatabaseWithTestUser();
        TrainingDto trainingDto = new TrainingDto("JTCNW", DONE, LocalDateTime.now());
        var training = trainingFactory.fromDto(trainingDto, user);
        Long id = trainingRepository.save(training).getId();
        //when
        underTest.deleteTraining(id);
        //then
        assertThat(trainingRepository.findById(id)).isEqualTo(Optional.empty());
    }

    @Test
    void shouldUpdate() {
        var training = initDatabaseWithTraining();
        TrainingDto trainingDto = new TrainingDto("New description", PLANNED, LocalDateTime.now());
        //when
        underTest.updateTraining(training.getId(), trainingDto);
        //then
        assertThat(training.getDescription()).isEqualTo("New description");
        assertThat(training.getStatus()).isEqualTo(PLANNED);
    }


    @ParameterizedTest
    @MethodSource("provideFilters")
    void shouldReturnListOfFilteredTrainingQueryDto(String from, String to, TrainingStatus trainingStatus, int size) {
        initDatabaseWithTrainings();
        Map<String, String> dateFilter = new HashMap<>();
        Map<String, String> statusFilter = new HashMap<>();
        dateFilter.put("from", from);
        dateFilter.put("to", to);
        statusFilter.put("status", trainingStatus.name());
        Map<String, String> filter = Stream.of(statusFilter, dateFilter)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
        var result = underTest.getFilteredTrainings(filter);
        System.out.println(result);
        assertThat(result).hasSize(size);
    }

    private static Stream<Arguments> provideFilters() {
        return Stream.of(
                Arguments.of("2022-04-09", "2022-04-11", DONE, 1),
                Arguments.of("2022-04-09", "2022-04-11", PLANNED, 0),
                Arguments.of("2022-04-01", "2022-04-30", DONE, 2),
                Arguments.of("2022-04-01", "2022-04-30", PLANNED, 1),
                Arguments.of("2021-04-01", "2021-04-30", PLANNED, 0)

        );
    }

    private static Stream<Arguments> provideTrainingStatus() {
        return Stream.of(
                Arguments.of(DONE, "planned"),
                Arguments.of(PLANNED, "done")
        );
    }
}