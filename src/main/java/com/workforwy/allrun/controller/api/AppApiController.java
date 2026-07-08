package com.workforwy.allrun.controller.api;

import com.workforwy.allrun.dto.ApiResponse;
import com.workforwy.allrun.dto.AppUpdateResponse;
import com.workforwy.allrun.service.AppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "应用")
@RestController
@RequestMapping("/api/v1/app")
public class AppApiController {

    private final AppService appService;

    public AppApiController(AppService appService) {
        this.appService = appService;
    }

    @Operation(summary = "检查 APK 更新")
    @GetMapping("/update")
    public ApiResponse<AppUpdateResponse> checkUpdate(
            @RequestParam(required = false) String username
    ) {
        return ApiResponse.ok(appService.checkUpdate(username));
    }
}
