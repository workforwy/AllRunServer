package com.workforwy.allrun.controller.api;

import com.workforwy.allrun.dto.ApiResponse;
import com.workforwy.allrun.dto.PageResponse;
import com.workforwy.allrun.dto.UserResponse;
import com.workforwy.allrun.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/users")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/me")
    public ApiResponse<UserResponse> me(@AuthenticationPrincipal String username) {
        return ApiResponse.ok(userService.getProfile(username));
    }

    @Operation(summary = "分页查询用户列表")
    @GetMapping
    public ApiResponse<PageResponse<UserResponse>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ApiResponse.ok(new PageResponse<>(page, size, userService.listUsers(page, size)));
    }
}
