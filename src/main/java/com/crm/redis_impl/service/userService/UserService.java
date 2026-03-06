package com.crm.redis_impl.service.userService;

import com.crm.redis_impl.dto.authDto.ChangePasswordDto;
import com.crm.redis_impl.dto.authDto.RegisterRequestDto;
import com.crm.redis_impl.dto.response.ApiResponse;
import com.crm.redis_impl.entity.user.UserEntity;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

public interface UserService {
   public ResponseEntity<ApiResponse> registerNewUser(RegisterRequestDto request);
   ResponseEntity<ApiResponse> getAllSalesAgents();
   ResponseEntity<ApiResponse> changeFirstLoginPassword(Long userId, ChangePasswordDto request);
   ResponseEntity<ApiResponse> softDeleteSalesAgent(Long agentId);
}