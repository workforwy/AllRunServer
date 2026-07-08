package com.workforwy.allrun.service;

import com.workforwy.allrun.common.Constants;
import com.workforwy.allrun.config.AppProperties;
import com.workforwy.allrun.entity.Topic;
import com.workforwy.allrun.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

@Service
public class TopicService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            "gif", "jpeg", "jpg", "png", "GIF", "JPEG", "JPG", "PNG"
    );

    private final TopicRepository topicRepository;
    private final AppProperties appProperties;

    public TopicService(TopicRepository topicRepository, AppProperties appProperties) {
        this.topicRepository = topicRepository;
        this.appProperties = appProperties;
    }

    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    public int addTopic(Topic topic, MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            return Constants.STATUS_NO_FILE;
        }

        String originalName = Path.of(imageFile.getOriginalFilename() != null ? imageFile.getOriginalFilename() : "")
                .getFileName()
                .toString();
        int dot = originalName.lastIndexOf('.');
        if (dot < 0) {
            return Constants.STATUS_NO_FILE;
        }
        String ext = originalName.substring(dot + 1);
        if (!ALLOWED_EXTENSIONS.contains(ext)) {
            return Constants.STATUS_NO_FILE;
        }

        Path uploadDir = Path.of(appProperties.getUploadDir(), "topicImage").toAbsolutePath().normalize();
        Files.createDirectories(uploadDir);
        imageFile.transferTo(uploadDir.resolve(originalName));

        String imageUrl = appProperties.getPublicBaseUrl() + "/topicImage/" + originalName;
        topic.setImageUrl(imageUrl);
        topicRepository.save(topic);
        return Constants.STATUS_OK;
    }
}
