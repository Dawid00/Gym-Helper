package com.depe.gymhelper.auth;


import com.depe.gymhelper.user.UserDetailsServiceImpl;
import com.depe.gymhelper.user.UserRegisterDto;
import com.depe.gymhelper.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
class AuthenticationController {

    private final UserService userService;
    private final TokenService tokenService;
    private final UserDetailsServiceImpl userDetailsService;

    AuthenticationController(UserService userService, TokenService tokenService, UserDetailsServiceImpl userDetailsService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    void registerUser(@RequestBody UserRegisterDto userRegisterDto){
        userService.createUser(userRegisterDto);
    }

    @PostMapping("/login")
    ResponseEntity<AuthenticationResponseDto> loginUser(@RequestBody AuthenticationRequestDto authenticationRequestDto){
        return ResponseEntity.ok(new AuthenticationResponseDto(tokenService.createToken(userDetailsService.loadUserByUsername(authenticationRequestDto.getUsername()))));
    }

}
