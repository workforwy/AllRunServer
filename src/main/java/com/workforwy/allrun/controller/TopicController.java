package com.workforwy.allrun.controller;

import com.workforwy.allrun.common.Constants;
import com.workforwy.allrun.entity.Topic;
import com.workforwy.allrun.service.TopicService;
import com.workforwy.allrun.service.UserService;
import com.workforwy.allrun.util.LegacyJson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@RestController
public class TopicController {

    private final TopicService topicService;
    private final UserService userService;

    public TopicController(TopicService topicService, UserService userService) {
        this.topicService = topicService;
        this.userService = userService;
    }

    @PostMapping(value = "/addTopic.jsp", produces = MediaType.TEXT_HTML_VALUE)
    public String addTopic(MultipartHttpServletRequest request) {
        int status = Constants.STATUS_OK;
        String msg = Constants.STATUS_OK_MSG;
        String imageUrl = "";

        try {
            String username = request.getParameter("username");
            String content = request.getParameter("content");
            String address = request.getParameter("address");
            String latitude = request.getParameter("latitude");
            String longitude = request.getParameter("longitude");

            if (latitude == null || latitude.isBlank()) {
                latitude = "0.000000";
            }
            if (longitude == null || longitude.isBlank()) {
                longitude = "0.000000";
            }

            MultipartFile imageFile = null;
            for (MultipartFile file : request.getFileMap().values()) {
                if (!file.isEmpty()) {
                    imageFile = file;
                    break;
                }
            }

            Topic topic = new Topic();
            topic.setUsername(username);
            topic.setContent(content);
            topic.setAddress(address);
            topic.setLatitude(Double.parseDouble(latitude));
            topic.setLongitude(Double.parseDouble(longitude));
            topic.setCreateTime(System.currentTimeMillis());

            status = topicService.addTopic(topic, imageFile);
            if (status == Constants.STATUS_OK) {
                imageUrl = topic.getImageUrl();
            } else {
                msg = Constants.STATUS_NO_FILE_MSG;
            }
        } catch (Exception e) {
            status = Constants.STATUS_NO_FILE;
            msg = Constants.STATUS_NO_FILE_MSG;
        }

        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        buffer.append("\"status\":\"").append(status).append("\",");
        buffer.append("\"msg\":\"").append(LegacyJson.escape(msg)).append("\"");
        if (status == Constants.STATUS_OK) {
            buffer.append(",\"imageUrl\":\" ").append(LegacyJson.escape(imageUrl)).append("\"");
        }
        buffer.append("}");
        return buffer.toString();
    }

    @PostMapping(value = "/queryTopic.jsp", produces = MediaType.TEXT_HTML_VALUE)
    public String queryTopic(@RequestParam String username, @RequestParam String md5password) {
        int status = Constants.STATUS_OK;
        String msg = Constants.STATUS_OK_MSG;
        List<Topic> topics = List.of();

        try {
            if (!userService.authenticate(username, md5password)) {
                status = Constants.STATUS_LOGIN_ERROR;
                msg = Constants.STATUS_LOGIN_ERROR_MSG;
            } else {
                topics = topicService.findAll();
            }
        } catch (Exception e) {
            status = Constants.STATUS_SERVER_ERROR;
            msg = Constants.STATUS_SERVER_ERROR_MSG;
        }

        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        buffer.append("\"status\":\"").append(status).append("\",");
        buffer.append("\"msg\":\"").append(LegacyJson.escape(msg)).append("\"");
        if (status == Constants.STATUS_OK) {
            buffer.append(",\"data\":[");
            for (int i = 0; i < topics.size(); i++) {
                Topic topic = topics.get(i);
                buffer.append("{");
                buffer.append("\"id\":").append(topic.getId()).append(",");
                buffer.append("\"username\":\"").append(LegacyJson.escape(topic.getUsername())).append("\",");
                buffer.append("\"content\":\"").append(LegacyJson.escape(topic.getContent())).append("\",");
                buffer.append("\"imageUrl\":\"").append(LegacyJson.escape(topic.getImageUrl())).append("\",");
                buffer.append("\"latitude\":").append(topic.getLatitude()).append(",");
                buffer.append("\"longitude\":").append(topic.getLongitude()).append(",");
                buffer.append("\"address\":\"").append(LegacyJson.escape(topic.getAddress())).append("\",");
                // 保留原 JSP 中的字段名拼写（createTime 后带空格）
                buffer.append("\"createTime \":").append(topic.getCreateTime());
                buffer.append("}");
                if (i < topics.size() - 1) {
                    buffer.append(",");
                }
            }
            buffer.append("]");
        }
        buffer.append("}");
        return buffer.toString();
    }
}
