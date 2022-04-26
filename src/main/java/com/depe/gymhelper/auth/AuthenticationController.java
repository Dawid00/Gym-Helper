package com.depe.gymhelper.auth;




import com.depe.gymhelper.user.RegisterUserRequest;
import com.depe.gymhelper.user.UserDetailsServiceImpl;
import com.depe.gymhelper.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
