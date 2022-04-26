package com.depe.gymhelper.auth;




import com.depe.gymhelper.user.EmailRequest;
import com.depe.gymhelper.user.RegisterUserRequest;
import com.depe.gymhelper.user.UserService;
import com.depe.gymhelper.user.UsernameRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    AuthenticationController(final AuthenticationService authenticationService, final UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/register")
    void registerUser(@RequestBody RegisterUserRequest registerUserRequest){
        userService.createUser(registerUserRequest);
    }

    @PostMapping("/login")
    ResponseEntity<AuthenticationResponseDto> loginUser(@RequestBody AuthenticationRequestDto authenticationRequestDto){
        return ResponseEntity.ok(authenticationService.authenticateUser(authenticationRequestDto));
    }

    @GetMapping("/check/username")
    ResponseEntity<Boolean> isUsernameAvailable(@Valid @RequestBody UsernameRequest usernameRequest) {
        return ResponseEntity.ok(userService.isUsernameAvailable(usernameRequest.getUsername()));
    }

    @GetMapping("/check/email")
    ResponseEntity<Boolean> isEmailAvailable(@Valid @RequestBody EmailRequest emailRequest) {
        return ResponseEntity.ok(userService.isEmailAvailable(emailRequest.getEmail()));
    }
}
