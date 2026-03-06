package com.crm.redis_impl.dto.authDto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String username;
    private String email;
    private String password;
    private String role; // e.g., "ROLE_SALES"
}