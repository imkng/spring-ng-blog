package com.programming.springblog.service;

import com.programming.springblog.dto.LoginRequest;
import com.programming.springblog.dto.RegisterRequest;
import com.programming.springblog.model.User;
import com.programming.springblog.repository.UserRepository;
import com.programming.springblog.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;
    public void signUp(RegisterRequest request) {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(encodePassword(request.getPassword()));
        userRepository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public String login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return jwtProvider.generateToken(authenticate);
    }
}
