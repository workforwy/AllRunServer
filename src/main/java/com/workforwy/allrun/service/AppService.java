package com.workforwy.allrun.service;

import com.workforwy.allrun.config.AppProperties;
import com.workforwy.allrun.dto.AppUpdateResponse;
import com.workforwy.allrun.exception.ApiException;
import org.springframework.stereotype.Service;

@Service
public class AppService {

    private final AppProperties appProperties;

    public AppService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public AppUpdateResponse checkUpdate(String username) {
        if ("999999".equals(username)) {
            throw ApiException.noUpdate("没有新版本");
        }
        return new AppUpdateResponse(
                appProperties.getApk().getVersion(),
                appProperties.getApk().getChangeLog(),
                appProperties.getPublicBaseUrl() + "/v10.apk"
        );
    }
}
