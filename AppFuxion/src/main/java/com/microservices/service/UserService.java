package com.microservices.service;

import com.microservices.model.dto.AuthDto;
import com.microservices.model.dto.LoginDto;
import com.microservices.model.entity.User;
import com.microservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public User addUser(User user){
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUserProfile(String token, User updateRequest) {
        String username = jwtService.extractUsername(token);
        User oldUser = userRepository.findByUsername(username);

        if (oldUser == null) {
            throw new RuntimeException("User not found");
        }
        if (updateRequest.getName() != null) {
            oldUser.setName(updateRequest.getName());
        }
        if (updateRequest.getEmail() != null) {
            oldUser.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getAge() > 0) {
            oldUser.setAge(updateRequest.getAge());
        }

        return userRepository.save(oldUser);
    }

    public String authenticateAndGetToken(LoginDto loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return jwtService.generateToken(user.getUsername());
        }
        throw new RuntimeException("Invalid username or password");
    }

    public User getUserProfileFromToken(String token) {
        String username = jwtService.extractUsername(token);
        return userRepository.findByUsername(username);
    }

    public AuthDto login(LoginDto loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthDto(token, user.getUsername(), user.getName(), user.getEmail(), user.getAge());
    }

}
