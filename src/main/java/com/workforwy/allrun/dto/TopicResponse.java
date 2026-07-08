package com.workforwy.allrun.dto;

import com.workforwy.allrun.entity.Topic;

public record TopicResponse(
        int id,
        String username,
        String content,
        String imageUrl,
        String address,
        double latitude,
        double longitude,
        long createTime
) {

    public static TopicResponse from(Topic topic) {
        return new TopicResponse(
                topic.getId(),
                topic.getUsername(),
                topic.getContent(),
                topic.getImageUrl(),
                topic.getAddress(),
                topic.getLatitude(),
                topic.getLongitude(),
                topic.getCreateTime()
        );
    }
}
