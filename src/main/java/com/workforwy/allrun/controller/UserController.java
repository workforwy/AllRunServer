package com.workforwy.allrun.controller;

import com.workforwy.allrun.common.Constants;
import com.workforwy.allrun.entity.User;
import com.workforwy.allrun.service.UserService;
import com.workforwy.allrun.util.LegacyJson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/queryUserDetail.jsp", produces = MediaType.TEXT_HTML_VALUE)
    public String queryUserDetail(@RequestParam String username, @RequestParam String md5password) {
        int status = Constants.STATUS_OK;
        String msg = Constants.STATUS_OK_MSG;
        User user = null;

        try {
            if (!userService.authenticate(username, md5password)) {
                status = Constants.STATUS_LOGIN_ERROR;
                msg = Constants.STATUS_LOGIN_ERROR_MSG;
            } else {
                user = userService.findByCredentials(username, md5password);
            }
        } catch (Exception e) {
            status = Constants.STATUS_SERVER_ERROR;
            msg = Constants.STATUS_SERVER_ERROR_MSG;
        }

        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        buffer.append("\"status\":\"").append(status).append("\",");
        buffer.append("\"msg\":\"").append(LegacyJson.escape(msg)).append("\"");
        if (status == Constants.STATUS_OK && user != null) {
            buffer.append(",\"data\":{");
            buffer.append("\"id\":").append(user.getId()).append(",");
            buffer.append("\"username\":\"").append(LegacyJson.escape(user.getUsername())).append("\",");
            buffer.append("\"md5password\":\"").append(LegacyJson.escape(user.getMd5password())).append("\",");
            buffer.append("\"nickname\":\"").append(LegacyJson.escape(user.getNickname())).append("\",");
            buffer.append("\"gender\":\"").append(LegacyJson.escape(user.getGender())).append("\",");
            buffer.append("\"iconUrl\":\"").append(LegacyJson.escape(user.getIconUrl())).append("\",");
            buffer.append("\"latitude\":").append(user.getLatitude()).append(",");
            buffer.append("\"longitude\":").append(user.getLongitude()).append(",");
            buffer.append("\"intro\":\"").append(LegacyJson.escape(user.getIntro())).append("\",");
            buffer.append("\"regTime\":").append(user.getRegTime());
            buffer.append("}");
        }
        buffer.append("}");
        return buffer.toString();
    }

    @PostMapping(value = "/queryNearbyUser.jsp", produces = MediaType.TEXT_HTML_VALUE)
    public String queryNearbyUser(@RequestParam String username,
                                  @RequestParam String md5password,
                                  @RequestParam(required = false) String pageIndex,
                                  @RequestParam(required = false) String rowNum) {
        int status = Constants.STATUS_OK;
        String msg = Constants.STATUS_OK_MSG;
        List<User> users = List.of();

        try {
            if (!userService.authenticate(username, md5password)) {
                status = Constants.STATUS_LOGIN_ERROR;
                msg = Constants.STATUS_LOGIN_ERROR_MSG;
            } else if (pageIndex == null || pageIndex.isBlank() || !pageIndex.matches("\\d+")) {
                status = Constants.STATUS_FAILURE;
                msg = "pageIndex参数为空或pageIndex不是数字";
            } else if (rowNum == null || rowNum.isBlank() || !rowNum.matches("\\d+")) {
                status = Constants.STATUS_FAILURE;
                msg = "rowNum为空或rowNum不是数字";
            } else {
                users = userService.findPage(Integer.parseInt(pageIndex), Integer.parseInt(rowNum));
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
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                buffer.append("{");
                buffer.append("\"id\":").append(user.getId()).append(",");
                buffer.append("\"username\":\"").append(LegacyJson.escape(user.getUsername())).append("\",");
                buffer.append("\"nickname\":\"").append(LegacyJson.escape(user.getNickname())).append("\",");
                buffer.append("\"gender\":\"").append(LegacyJson.escape(user.getGender())).append("\",");
                buffer.append("\"iconUrl\":\"").append(LegacyJson.escape(user.getIconUrl())).append("\",");
                buffer.append("\"latitude\":").append(user.getLatitude()).append(",");
                buffer.append("\"longitude\":").append(user.getLongitude()).append(",");
                buffer.append("\"intro\":\"").append(LegacyJson.escape(user.getIntro())).append("\",");
                buffer.append("\"regTime\":").append(user.getRegTime());
                buffer.append("}");
                if (i < users.size() - 1) {
                    buffer.append(",");
                }
            }
            buffer.append("]");
        }
        buffer.append("}");
        return buffer.toString();
    }
}
