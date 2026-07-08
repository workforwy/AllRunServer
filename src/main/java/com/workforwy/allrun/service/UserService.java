package com.workforwy.allrun.service;

import com.workforwy.allrun.common.Constants;
import com.workforwy.allrun.config.AppProperties;
import com.workforwy.allrun.entity.User;
import com.workforwy.allrun.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            "gif", "jpeg", "jpg", "png", "GIF", "JPEG", "JPG", "PNG"
    );

    private final UserRepository userRepository;
    private final AppProperties appProperties;

    public UserService(UserRepository userRepository, AppProperties appProperties) {
        this.userRepository = userRepository;
        this.appProperties = appProperties;
    }

    public boolean authenticate(String username, String md5password) {
        return userRepository.exists(username, md5password);
    }

    public User findByCredentials(String username, String md5password) {
        return userRepository.findByCredentials(username, md5password).orElse(null);
    }

    public List<User> findPage(int pageIndex, int rowNum) {
        return userRepository.findPage(pageIndex, rowNum);
    }

    public int register(User user, MultipartFile iconFile) throws IOException {
        if (iconFile == null || iconFile.isEmpty()) {
            return Constants.STATUS_NO_FILE;
        }

        String originalName = Path.of(iconFile.getOriginalFilename() != null ? iconFile.getOriginalFilename() : "")
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

        Path uploadDir = Path.of(appProperties.getUploadDir(), "userIcon").toAbsolutePath().normalize();
        Files.createDirectories(uploadDir);
        Path target = uploadDir.resolve(originalName);
        iconFile.transferTo(target);

        String iconUrl = appProperties.getPublicBaseUrl() + "/userIcon/" + originalName;
        user.setIconUrl(iconUrl);

        if (userRepository.existsByUsername(user.getUsername())) {
            return Constants.STATUS_REGISTER_ERROR;
        }

        userRepository.save(user);
        return Constants.STATUS_OK;
    }
}
