package com.depe.gymhelper.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    public UserService(UserRepository userRepository, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    public User getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username).get();
    }

    public void createUser(UserRegisterDto userRegisterDto) {
        if(userRepository.existsByUsername(userRegisterDto.getUsername())){
            throw new UserWithUsernameExistsException();
        }
        if(userRepository.existsByEmail(userRegisterDto.getEmail())){
            throw new UserWithEmailExistsException();
        }
        User user = userFactory.fromUserRegisterDto(userRegisterDto);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
    }

}
