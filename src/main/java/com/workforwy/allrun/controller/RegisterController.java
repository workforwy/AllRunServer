package com.workforwy.allrun.controller;

import com.workforwy.allrun.common.Constants;
import com.workforwy.allrun.entity.User;
import com.workforwy.allrun.service.UserService;
import com.workforwy.allrun.util.LegacyJson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register.jsp", produces = MediaType.TEXT_HTML_VALUE)
    public String register(MultipartHttpServletRequest request) {
        int status = Constants.STATUS_SERVER_ERROR;
        String msg = Constants.STATUS_SERVER_ERROR_MSG;
        String iconUrl = "";

        try {
            String username = request.getParameter("username");
            String md5password = request.getParameter("md5password");
            String nickname = request.getParameter("nickname");
            String gender = request.getParameter("gender");
            String intro = request.getParameter("intro");
            String latitude = request.getParameter("latitude");
            String longitude = request.getParameter("longitude");

            if (latitude == null || latitude.isBlank()) {
                latitude = "0.000000";
            }
            if (longitude == null || longitude.isBlank()) {
                longitude = "0.000000";
            }

            MultipartFile iconFile = request.getFile("iconUrl");
            if (iconFile == null) {
                for (MultipartFile file : request.getFileMap().values()) {
                    if (!file.isEmpty()) {
                        iconFile = file;
                        break;
                    }
                }
            }

            User user = new User();
            user.setUsername(username);
            user.setMd5password(md5password);
            user.setNickname(nickname);
            user.setGender(gender);
            user.setIntro(intro);
            user.setLatitude(Double.parseDouble(latitude));
            user.setLongitude(Double.parseDouble(longitude));
            user.setRegTime(System.currentTimeMillis());

            status = userService.register(user, iconFile);
            if (status == Constants.STATUS_OK) {
                msg = Constants.STATUS_OK_MSG;
                iconUrl = user.getIconUrl();
            } else if (status == Constants.STATUS_REGISTER_ERROR) {
                msg = Constants.STATUS_REGISTER_ERROR_MSG;
            } else if (status == Constants.STATUS_NO_FILE) {
                msg = Constants.STATUS_NO_FILE_MSG;
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
            buffer.append(",\"iconUrl\":\" ").append(LegacyJson.escape(iconUrl)).append("\"");
        }
        buffer.append("}");
        return buffer.toString();
    }
}
