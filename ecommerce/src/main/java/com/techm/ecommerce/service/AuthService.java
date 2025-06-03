package com.techm.ecommerce.service;

import com.techm.ecommerce.model.User;
import com.techm.ecommerce.request.LoginRequest;
import com.techm.ecommerce.response.AuthResponse;

public interface AuthService {
    public String signUp(User userDetails) throws Exception;
    public AuthResponse signin(LoginRequest request);
}
