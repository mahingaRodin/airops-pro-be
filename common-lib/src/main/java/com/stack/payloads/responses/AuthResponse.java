package com.stack.payloads.responses;

import com.stack.payloads.dtos.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthResponse {
    private String token;
    private String message;
    private String title;
    private UserDTO user;
}
