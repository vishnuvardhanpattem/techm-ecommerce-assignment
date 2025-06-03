package com.techm.ecommerce.service.impl;

import com.techm.ecommerce.config.JwtProvider;
import com.techm.ecommerce.domain.USER_ROLE;
import com.techm.ecommerce.model.User;
import com.techm.ecommerce.repository.UserRepository;
import com.techm.ecommerce.request.LoginRequest;
import com.techm.ecommerce.response.AuthResponse;
import com.techm.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;


    public String signUp(User userDetails) throws Exception {
        User user = userRepository.findUserByEmail(userDetails.getEmail());
        if (user != null) {
            throw new Exception("User already existed with this email");
        }
        User createdUser = new User();
        createdUser.setEmail(userDetails.getEmail());
        createdUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        createdUser.setFullName(userDetails.getFullName());
        createdUser.setRole(USER_ROLE.ROLE_USER);
        createdUser.setMobileNumber(userDetails.getMobileNumber());
        userRepository.save(createdUser);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDetails.getEmail(),
                        userDetails.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        return jwtProvider.generateToken(auth);

    }

    public AuthResponse signin(LoginRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail());
        if (user == null) {
            throw new BadCredentialsException("Invalid email or password");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Success");
        authResponse.setRole(user.getRole());

        return authResponse;
    }
}
