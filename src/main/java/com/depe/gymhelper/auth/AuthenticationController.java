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

    private final TokenService tokenService;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    AuthenticationController(final TokenService tokenService, final UserDetailsServiceImpl userDetailsService, final UserService userService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/register")
    void registerUser(@RequestBody RegisterUserRequest registerUserRequest){
        userService.createUser(registerUserRequest);
    }

    @PostMapping("/login")
    ResponseEntity<AuthenticationResponseDto> loginUser(@RequestBody AuthenticationRequestDto authenticationRequestDto){
        return ResponseEntity.ok(new AuthenticationResponseDto(tokenService.createToken(userDetailsService.loadUserByUsername(authenticationRequestDto.getUsername()))));
    }
}
