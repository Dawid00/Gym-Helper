package com.depe.gymhelper.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUserService {

    private final UserRepository userRepository;

    public AuthenticationUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserQueryEntity getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails =  (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
//        String username = "rosa12";
        var user = userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
        return new UserQueryEntity(user.getId(), user.getUsername());
    }
}
