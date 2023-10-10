package com.programming.springblog.controller;

import com.programming.springblog.dto.LoginRequest;
import com.programming.springblog.dto.RegisterRequest;
import com.programming.springblog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody RegisterRequest request){
        authService.signUp(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("login")
    public String login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
