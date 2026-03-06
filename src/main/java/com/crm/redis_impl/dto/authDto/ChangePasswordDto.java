package com.crm.redis_impl.dto.authDto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String newPassword;
    private String confirmPassword;
}