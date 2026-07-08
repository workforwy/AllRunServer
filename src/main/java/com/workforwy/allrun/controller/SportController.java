package com.workforwy.allrun.controller;

import com.workforwy.allrun.common.Constants;
import com.workforwy.allrun.entity.Trace;
import com.workforwy.allrun.service.SportService;
import com.workforwy.allrun.service.SportService.SportWithTraces;
import com.workforwy.allrun.service.UserService;
import com.workforwy.allrun.util.LegacyJson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SportController {

    private final SportService sportService;
    private final UserService userService;

    public SportController(SportService sportService, UserService userService) {
        this.sportService = sportService;
        this.userService = userService;
    }

    @PostMapping(value = "/addSportData.jsp", produces = MediaType.TEXT_HTML_VALUE)
    public String addSportData(@RequestParam String username,
                               @RequestParam String md5password,
                               @RequestParam String sportType,
                               @RequestParam String data) {
        int status = Constants.STATUS_OK;
        String msg = Constants.STATUS_OK_MSG;

        try {
            if (!userService.authenticate(username, md5password)) {
                status = Constants.STATUS_LOGIN_ERROR;
                msg = Constants.STATUS_LOGIN_ERROR_MSG;
            } else {
                sportService.addSportData(username, sportType, data);
            }
        } catch (Exception e) {
            status = Constants.STATUS_SERVER_ERROR;
            msg = Constants.STATUS_SERVER_ERROR_MSG;
        }

        return "{\"status\":\"" + status + "\",\"msg\":\"" + LegacyJson.escape(msg) + "\"}";
    }

    @PostMapping(value = "/queryNearbySportData.jsp", produces = MediaType.TEXT_HTML_VALUE)
    public String queryNearbySportData(@RequestParam String username,
                                       @RequestParam String md5password,
                                       @RequestParam String latitude,
                                       @RequestParam String longitude) {
        int status = Constants.STATUS_OK;
        String msg = Constants.STATUS_OK_MSG;
        List<SportWithTraces> sports = List.of();

        try {
            if (!userService.authenticate(username, md5password)) {
                status = Constants.STATUS_LOGIN_ERROR;
                msg = Constants.STATUS_LOGIN_ERROR_MSG;
            } else {
                // 原实现忽略经纬度，返回全部运动记录
                sports = sportService.findSportsWithTraces();
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
            buffer.append(",\"sportData\":[");
            for (int i = 0; i < sports.size(); i++) {
                SportWithTraces item = sports.get(i);
                buffer.append("{");
                buffer.append("\"id\":").append(item.sport().getId()).append(",");
                buffer.append("\"username\":\"").append(LegacyJson.escape(item.sport().getUsername())).append("\",");
                buffer.append("\"sportType\":\"").append(LegacyJson.escape(item.sport().getSportType())).append("\",");
                buffer.append("\"traceData\":[");
                List<Trace> traces = item.traces();
                for (int t = 0; t < traces.size(); t++) {
                    Trace trace = traces.get(t);
                    buffer.append("{");
                    buffer.append("\"id\":").append(trace.getId()).append(",");
                    buffer.append("\"sportId\":").append(trace.getSportId()).append(",");
                    buffer.append("\"sportTime\":").append(trace.getSportTime()).append(",");
                    buffer.append("\"latitude\":").append(trace.getLatitude()).append(",");
                    buffer.append("\"longitude\":").append(trace.getLongitude());
                    buffer.append("}");
                    if (t < traces.size() - 1) {
                        buffer.append(",");
                    }
                }
                buffer.append("]");
                buffer.append("}");
                if (i < sports.size() - 1) {
                    buffer.append(",");
                }
            }
            buffer.append("]");
        }
        buffer.append("}");
        return buffer.toString();
    }
}
