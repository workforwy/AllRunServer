package com.workforwy.allrun.service;

import com.workforwy.allrun.config.AppProperties;
import com.workforwy.allrun.dto.LoginRequest;
import com.workforwy.allrun.dto.LoginResponse;
import com.workforwy.allrun.dto.UserResponse;
import com.workforwy.allrun.entity.User;
import com.workforwy.allrun.exception.ApiException;
import com.workforwy.allrun.repository.UserRepository;
import com.workforwy.allrun.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            "gif", "jpeg", "jpg", "png"
    );

    private final UserRepository userRepository;
    private final AppProperties appProperties;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       AppProperties appProperties,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.appProperties = appProperties;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> ApiException.unauthorized("账户登录不成功，账号或者密码错误！"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw ApiException.unauthorized("账户登录不成功，账号或者密码错误！");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new LoginResponse(token, UserResponse.from(user));
    }

    public UserResponse register(String username,
                                 String password,
                                 String nickname,
                                 String gender,
                                 String intro,
                                 Double latitude,
                                 Double longitude,
                                 MultipartFile icon) throws IOException {
        if (icon == null || icon.isEmpty()) {
            throw ApiException.noFile("没有上传图片");
        }

        if (userRepository.existsByUsername(username)) {
            throw ApiException.conflict("用户已经存在");
        }

        String storedFileName = storeImage(icon, "userIcon");
        String iconUrl = appProperties.getPublicBaseUrl() + "/userIcon/" + storedFileName;

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setNickname(nickname);
        user.setGender(gender);
        user.setIntro(intro != null ? intro : "");
        user.setLatitude(latitude != null ? latitude : 0);
        user.setLongitude(longitude != null ? longitude : 0);
        user.setIconUrl(iconUrl);
        user.setRegTime(System.currentTimeMillis());

        userRepository.save(user);
        return userRepository.findByUsername(username)
                .map(UserResponse::from)
                .orElseThrow(() -> ApiException.serverError("注册失败"));
    }

    public UserResponse getProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> ApiException.unauthorized("用户不存在"));
        return UserResponse.from(user);
    }

    public List<UserResponse> listUsers(int page, int size) {
        if (page < 1 || size < 1) {
            throw ApiException.badRequest("page 和 size 必须大于 0");
        }
        return userRepository.findPage(page, size).stream()
                .map(UserResponse::from)
                .toList();
    }

    public User requireUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> ApiException.unauthorized("用户不存在"));
    }

    private String storeImage(MultipartFile file, String subDir) throws IOException {
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
        Path uploadDir = Path.of(appProperties.getUploadDir(), subDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadDir);
        file.transferTo(uploadDir.resolve(storedName));
        return storedName;
    }
}
