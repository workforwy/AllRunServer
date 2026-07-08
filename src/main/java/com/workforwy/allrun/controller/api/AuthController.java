package com.workforwy.allrun.controller.api;

import com.workforwy.allrun.dto.ApiResponse;
import com.workforwy.allrun.dto.LoginRequest;
import com.workforwy.allrun.dto.LoginResponse;
import com.workforwy.allrun.dto.UserResponse;
import com.workforwy.allrun.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "认证")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.ok(userService.login(request));
    }

    @Operation(summary = "用户注册（含头像上传）")
    @PostMapping("/register")
    public ApiResponse<UserResponse> register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String nickname,
            @RequestParam String gender,
            @RequestParam(required = false) String intro,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam("icon") MultipartFile icon
    ) throws IOException {
        return ApiResponse.ok(userService.register(
                username, password, nickname, gender, intro, latitude, longitude, icon
        ));
    }
}
