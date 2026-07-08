package com.workforwy.allrun.dto;

import com.workforwy.allrun.entity.User;

public record UserResponse(
        int id,
        String username,
        String nickname,
        String gender,
        String iconUrl,
        double latitude,
        double longitude,
        String intro,
        long regTime
) {

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getGender(),
                user.getIconUrl(),
                user.getLatitude(),
                user.getLongitude(),
                user.getIntro(),
                user.getRegTime()
        );
    }
}
