package com.stack.services;

import com.stack.payloads.dtos.UserDTO;
import com.stack.payloads.responses.AuthResponse;

public interface AuthService {
    AuthResponse login(String email , String password);
    AuthResponse signup(UserDTO req) throws Exception;
}
