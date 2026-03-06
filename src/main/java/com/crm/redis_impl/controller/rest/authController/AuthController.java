package com.crm.redis_impl.controller.rest.authController;

import com.crm.redis_impl.dto.authDto.ChangePasswordDto;
import com.crm.redis_impl.dto.authDto.RegisterRequestDto;
import com.crm.redis_impl.dto.response.ApiResponse;
import com.crm.redis_impl.entity.user.UserEntity;
import com.crm.redis_impl.repository.user.UserRepository;
import com.crm.redis_impl.security.CustomUserDetails;
import com.crm.redis_impl.service.userService.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegisterRequestDto request) {
        if("ROLE_SALES".equalsIgnoreCase(request.getRole())){
            return new ResponseEntity<>(
                    new ApiResponse("false", "Unauthorized: You cannot register as a Sales Agent. Please contact an Administrator.", null),
                    HttpStatus.FORBIDDEN
            );
        }
        return userService.registerNewUser(request);
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse> updatePassword(@RequestBody ChangePasswordDto request) {
        // Securely grab the user ID from the active session
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = customUserDetails.getId();

        return userService.changeFirstLoginPassword(userId, request);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> checkStatus(@AuthenticationPrincipal CustomUserDetails user) {
        Map<String, Object> response = new HashMap<>();

        // Check if user exists and is not marked as deleted
        Integer exists = userRepository.activeById(user.getId());

        if (exists == 1) {
            response.put("isDeleted", true);
        } else {
            response.put("isDeleted", false);
        }

        return ResponseEntity.ok(response);
    }
}