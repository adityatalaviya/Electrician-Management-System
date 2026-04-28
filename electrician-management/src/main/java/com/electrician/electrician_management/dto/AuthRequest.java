package com.electrician.electrician_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AuthRequest {
    private Long id;
    private String email;
    private String password;
}
