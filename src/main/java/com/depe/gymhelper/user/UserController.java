package com.depe.gymhelper.user;

import com.depe.gymhelper.training.TrainingQueryDto;
import com.depe.gymhelper.training.TrainingQueryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
class UserController {

    private final UserQueryRepository userQueryRepository;
    private final TrainingQueryRepository trainingQueryRepository;
    private final UserService userService;
    private final AuthenticationUserService authenticationUserService;

    UserController(final UserQueryRepository userQueryRepository, final TrainingQueryRepository trainingQueryRepository, final UserService userService, final AuthenticationUserService authenticationUserService) {
        this.userQueryRepository = userQueryRepository;
        this.trainingQueryRepository = trainingQueryRepository;
        this.userService = userService;
        this.authenticationUserService = authenticationUserService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/me/info")
    ResponseEntity<UserQueryDto> getInfoAboutMe() {
        var user = authenticationUserService.getLoggedUser();
        return ResponseEntity.ok(userQueryRepository.findByUsername(user.getUsername()).get());
    }
    @GetMapping("/{username}/trainings")
    ResponseEntity<List<TrainingQueryDto>> getTrainingsByUsername(@PathVariable String username){
        var user = userService.getQueryUserByUsername(username);
        return ResponseEntity.ok(trainingQueryRepository.findAllTrainingDtoByUser(user));
    }

    @GetMapping("/{username}/info")
    ResponseEntity<UserQueryDto> getInfoAboutUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userQueryRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username)));
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> addUser(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        userService.createUser(registerUserRequest);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> updateUser(@PathVariable String username, @Valid @RequestBody RegisterUserRequest registerUserRequest) {
        userService.updateUserByUsername(username, registerUserRequest);
        return ResponseEntity.status(204).build();
    }

    @PutMapping()
    ResponseEntity<?> updateLoggedUser(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        userService.updateLoggedUser(registerUserRequest);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{username}/give/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> giveAdminRoleToUser(@PathVariable String username) {
        userService.giveAdminRoleToUser(username);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{username}/take/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> takeAdminRoleFromUser(@PathVariable String username) {
        userService.takeAdminRoleFromUser(username);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/change/password")
    ResponseEntity<?> changePasswordByLoggedUser(@Valid @RequestBody PasswordRequest passwordRequest) {
        userService.changePassword(passwordRequest);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/change/email")
    ResponseEntity<?> changeEmailByLoggedUser(@Valid @RequestBody EmailRequest emailRequest) {
        userService.changeEmail(emailRequest);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping()
    ResponseEntity<?> deleteLoggedUser() {
        userService.deleteLoggedUser();
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.status(204).build();
    }
}