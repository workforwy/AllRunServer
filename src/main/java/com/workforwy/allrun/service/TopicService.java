package com.workforwy.allrun.service;

import com.workforwy.allrun.config.AppProperties;
import com.workforwy.allrun.dto.TopicResponse;
import com.workforwy.allrun.entity.Topic;
import com.workforwy.allrun.exception.ApiException;
import com.workforwy.allrun.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class TopicService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            "gif", "jpeg", "jpg", "png"
    );

    private final TopicRepository topicRepository;
    private final AppProperties appProperties;

    public TopicService(TopicRepository topicRepository, AppProperties appProperties) {
        this.topicRepository = topicRepository;
        this.appProperties = appProperties;
    }

    public List<TopicResponse> findAll() {
        return topicRepository.findAll().stream()
                .map(TopicResponse::from)
                .toList();
    }

    public TopicResponse create(String username,
                                String content,
                                String address,
                                Double latitude,
                                Double longitude,
                                MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) {
            throw ApiException.noFile("没有上传图片");
        }

        String storedName = storeImage(image);
        String imageUrl = appProperties.getPublicBaseUrl() + "/topicImage/" + storedName;

        Topic topic = new Topic();
        topic.setUsername(username);
        topic.setContent(content);
        topic.setAddress(address != null ? address : "");
        topic.setLatitude(latitude != null ? latitude : 0);
        topic.setLongitude(longitude != null ? longitude : 0);
        topic.setImageUrl(imageUrl);
        topic.setCreateTime(System.currentTimeMillis());

        int topicId = topicRepository.save(topic);
        return topicRepository.findById(topicId)
                .map(TopicResponse::from)
                .orElseThrow(() -> ApiException.serverError("发布话题失败"));
    }

    private String storeImage(MultipartFile file) throws IOException {
        String originalName = Path.of(file.getOriginalFilename() != null ? file.getOriginalFilename() : "")
                .getFileName()
                .toString();
        int dot = originalName.lastIndexOf('.');
        if (dot < 0) {
            throw ApiException.noFile("没有上传图片");
        }
        String ext = originalName.substring(dot + 1).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(ext)) {
            throw ApiException.badRequest("不支持的图片格式: " + ext);
        }

        String storedName = UUID.randomUUID() + "." + ext;
        Path uploadDir = Path.of(appProperties.getUploadDir(), "topicImage").toAbsolutePath().normalize();
        Files.createDirectories(uploadDir);
        file.transferTo(uploadDir.resolve(storedName));
        return storedName;
    }
}
