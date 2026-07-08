package com.workforwy.allrun.controller.api;

import com.workforwy.allrun.dto.ApiResponse;
import com.workforwy.allrun.dto.TopicResponse;
import com.workforwy.allrun.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "话题")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/topics")
public class TopicApiController {

    private final TopicService topicService;

    public TopicApiController(TopicService topicService) {
        this.topicService = topicService;
    }

    @Operation(summary = "发布话题（含图片）")
    @PostMapping
    public ApiResponse<TopicResponse> create(
            @AuthenticationPrincipal String username,
            @RequestParam String content,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam("image") MultipartFile image
    ) throws IOException {
        return ApiResponse.ok(topicService.create(username, content, address, latitude, longitude, image));
    }

    @Operation(summary = "查询话题列表")
    @GetMapping
    public ApiResponse<List<TopicResponse>> list() {
        return ApiResponse.ok(topicService.findAll());
    }
}
