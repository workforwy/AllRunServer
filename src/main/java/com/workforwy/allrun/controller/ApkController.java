package com.workforwy.allrun.controller;

import com.workforwy.allrun.common.Constants;
import com.workforwy.allrun.config.AppProperties;
import com.workforwy.allrun.util.LegacyJson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApkController {

    private final AppProperties appProperties;

    public ApkController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @GetMapping(value = "/apkUpdate.jsp", produces = MediaType.TEXT_HTML_VALUE)
    public String apkUpdate(@RequestParam(required = false) String username) {
        int status = Constants.STATUS_OK;
        String msg = Constants.STATUS_OK_MSG;

        if ("999999".equals(username)) {
            status = Constants.STATUS_WITHOUT_RELEASE;
            msg = Constants.STATUS_WITHOUT_RELEASE_MSG;
        }

        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        buffer.append("\"status\":\"").append(status).append("\",");
        buffer.append("\"msg\":\"").append(LegacyJson.escape(msg)).append("\"");
        if (status == Constants.STATUS_OK) {
            buffer.append(",\"version\":\"").append(appProperties.getApk().getVersion()).append("\",");
            buffer.append("\"changeLog\":\"").append(LegacyJson.escape(appProperties.getApk().getChangeLog())).append("\",");
            buffer.append("\"apkUrl\":\"").append(appProperties.getPublicBaseUrl()).append("/v10.apk\"");
        }
        buffer.append("}");
        return buffer.toString();
    }
}
