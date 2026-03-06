package com.crm.redis_impl.service.userService.impl;

import com.crm.redis_impl.dto.authDto.ChangePasswordDto;
import com.crm.redis_impl.dto.authDto.RegisterRequestDto;
import com.crm.redis_impl.dto.response.ApiResponse;
import com.crm.redis_impl.dto.userDto.UserResponseDto;
import com.crm.redis_impl.entity.role.RoleEntity;
import com.crm.redis_impl.entity.user.UserEntity;
import com.crm.redis_impl.repository.role.RoleRepository;
import com.crm.redis_impl.repository.user.UserRepository;
import com.crm.redis_impl.security.CustomUserDetails;
import com.crm.redis_impl.service.redisService.RedisService;
import com.crm.redis_impl.service.userService.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private RedisService redisService;

    @Override
    public ResponseEntity<ApiResponse> registerNewUser(RegisterRequestDto  request) {
        if (userRepository.existsByUsername(request.getUsername()) || userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Username or Email already exists!");
        }
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());


        user.setPassword(passwordEncoder.encode(user.getPassword()));

        RoleEntity role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found."));

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
        redisService.delete("ADMIN");
        return ResponseEntity.ok(new ApiResponse("true", "Registration successful! Please log in.", null));
    }

    @Override
    public ResponseEntity<ApiResponse> getAllSalesAgents() {

        List<UserResponseDto>  userDtos = redisService.get("ADMIN", List.class);
        if (userDtos != null) {
            log.info("Admin Cached Hit.");
            return ResponseEntity.ok(new ApiResponse("true", "Sales agents fetched successfully", userDtos));
        }

        List<UserEntity> salesUsers = userRepository.findActiveUsersByRoleNative("ROLE_SALES", 0);

        userDtos = salesUsers.stream().map(user -> {
            UserResponseDto dto = new UserResponseDto();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            return dto;
        }).collect(Collectors.toList());
        redisService.set("ADMIN", userDtos, 3600L);
        return ResponseEntity.ok(new ApiResponse("true", "Sales agents fetched successfully", userDtos));
    }

    @Override
    public ResponseEntity<ApiResponse> softDeleteSalesAgent(Long agentId) {
        UserEntity agent = userRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        userRepository.deactivateUserNative(agentId);

        String agentUsername = agent.getUsername();
        simpMessagingTemplate.convertAndSendToUser(agentUsername, "/queue/deactivation", "LOCKED");
        redisService.delete("ADMIN");
        return ResponseEntity.ok(new ApiResponse("true", "Agent successfully deactivated.", null));
    }

    @Override
    public ResponseEntity<ApiResponse> changeFirstLoginPassword(Long userId, ChangePasswordDto request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(new ApiResponse("false", "Passwords do not match.", null));
        }

        String encodedPassword = passwordEncoder.encode(request.getNewPassword());


        userRepository.updatePasswordAndStatusNative(userId, encodedPassword);

        return ResponseEntity.ok(new ApiResponse("true", "Password updated successfully. Please log in again.", null));
    }

}