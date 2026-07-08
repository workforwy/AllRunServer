package com.workforwy.allrun.controller.api;

import com.workforwy.allrun.dto.ApiResponse;
import com.workforwy.allrun.dto.CreateSportRequest;
import com.workforwy.allrun.dto.SportResponse;
import com.workforwy.allrun.service.SportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "运动")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/sports")
public class SportApiController {

    private final SportService sportService;

    public SportApiController(SportService sportService) {
        this.sportService = sportService;
    }

    @Operation(summary = "上传运动 GPS 轨迹")
    @PostMapping
    public ApiResponse<SportResponse> create(
            @AuthenticationPrincipal String username,
            @Valid @RequestBody CreateSportRequest request
    ) {
        return ApiResponse.ok(sportService.create(username, request));
    }

    @Operation(summary = "查询运动记录（含轨迹点）")
    @GetMapping
    public ApiResponse<List<SportResponse>> list() {
        return ApiResponse.ok(sportService.findAllWithTraces());
    }
}
