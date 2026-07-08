package com.workforwy.allrun.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class UploadDirectoryInitializer {

    private final AppProperties appProperties;

    public UploadDirectoryInitializer(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @PostConstruct
    public void init() throws IOException {
        Path root = Path.of(appProperties.getUploadDir()).toAbsolutePath().normalize();
        Files.createDirectories(root.resolve("userIcon"));
        Files.createDirectories(root.resolve("topicImage"));
    }
}
