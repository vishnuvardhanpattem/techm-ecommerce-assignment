package com.techm.ecommerce.controller;

import com.techm.ecommerce.config.JwtProvider;
import com.techm.ecommerce.domain.USER_ROLE;
import com.techm.ecommerce.model.User;
import com.techm.ecommerce.repository.UserRepository;
import com.techm.ecommerce.request.LoginRequest;
import com.techm.ecommerce.response.AuthResponse;
import com.techm.ecommerce.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody User user) throws Exception {
        String token = authService.signUp(user);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setRole(USER_ROLE.ROLE_USER);
        authResponse.setMessage("Sign up successful");
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) throws Exception {
        AuthResponse res = authService.signin(req);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
