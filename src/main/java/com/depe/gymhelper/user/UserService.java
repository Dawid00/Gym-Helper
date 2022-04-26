package com.depe.gymhelper.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final AuthenticationUserService authenticationUserService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserFactory userFactory;
    private final PasswordEncoder passwordEncoder;

    public UserService(final AuthenticationUserService authenticationUserService, final UserRepository userRepository, RoleRepository roleRepository, final UserFactory userFactory, final PasswordEncoder passwordEncoder) {
        this.authenticationUserService = authenticationUserService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userFactory = userFactory;
        this.passwordEncoder = passwordEncoder;
    }

    public Long createUser(RegisterUserRequest registerUserRequest) {
        if(userRepository.existsByUsername(registerUserRequest.getUsername())){
            throw new UserWithUsernameExistsException(registerUserRequest.getUsername());
        }
        if(userRepository.existsByEmail(registerUserRequest.getEmail())){
            throw new UserWithEmailExistsException(registerUserRequest.getEmail());
        }
        User user = userFactory.fromUserRegisterDto(registerUserRequest);
        user.addRole(roleRepository.findByType(RoleType.USER));
        return userRepository.save(user).getId();
    }

    @Transactional
    public void deleteUser(Long id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserQueryEntity getQueryUserByUsername(String username){
        var user = getUserByUsername(username);
        return new UserQueryEntity(user.getId(), user.getUsername());
    }
    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }

    @Transactional
    public void updateLoggedUser(RegisterUserRequest registerUserRequest){
        var user = getLoggedUser();
        registerUserRequest.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        if(isEmailAvailable(registerUserRequest.getEmail()) && isUsernameAvailable(registerUserRequest.getUsername())){
            user.updateUserByRequest(registerUserRequest);
        }
    }

    @Transactional
    public void updateUserByUsername(String username, RegisterUserRequest registerUserRequest) {
        var user = getUserByUsername(username);
        registerUserRequest.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user.updateUserByRequest(registerUserRequest);
    }

    @Transactional
    public void giveAdminRoleToUser(String username) {
        User user = getUserByUsername(username);
        Role admin = roleRepository.findByType(RoleType.ADMIN);
        user.addRole(admin);

    }

    @Transactional
    public void takeAdminRoleFromUser(String username) {
        User user = getUserByUsername(username);
        Role admin = roleRepository.findByType(RoleType.ADMIN);
        user.removeRole(admin);
    }

    @Transactional
    public void changePassword(PasswordRequest password) {
        var user = getLoggedUser();
        user.setPassword(passwordEncoder.encode(password.getPassword()));
    }

    @Transactional
    public void changeEmail(EmailRequest email) {
        var user = getLoggedUser();
        user.setEmail(email.getEmail());
    }

    @Transactional
    public void deleteLoggedUser() {
        userRepository.deleteByUsername(getLoggedUser().getUsername());
    }

    @Transactional
    public void deleteUserByUsername(String username) {
        if(userRepository.existsByUsername(username)){
            userRepository.deleteByUsername(username);
        }
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow( ()-> new UsernameNotFoundException(username));
    }

    private User getLoggedUser(){
        var queryUser = authenticationUserService.getLoggedUser();
        return userRepository.findByUsername(queryUser.getUsername()).get();
    }
}
