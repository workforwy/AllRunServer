# AllRunServer

跑步/运动类 Android App 后端，基于 **Spring Boot 3 REST API**。

## 技术栈

- Java 17 + Spring Boot 3.4
- Spring Security + JWT 认证
- Spring JDBC + MySQL + Flyway
- OpenAPI / Swagger UI 文档

## 快速开始

### 1. 创建数据库

```sql
CREATE DATABASE IF NOT EXISTS all_run DEFAULT CHARACTER SET utf8mb4;
```

### 2. 配置环境变量

```bash
export DB_USER=root
export DB_PASSWORD=your_password
export JWT_SECRET=your-long-random-secret-at-least-32-chars
export PUBLIC_BASE_URL=http://localhost:8080
```

### 3. 启动（本地开发，无需 MySQL）

默认使用嵌入式 H2 数据库，直接运行：

```bash
cd AllRunServer
mvn spring-boot:run
```

生产环境使用 MySQL：

```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_USER=root
export DB_PASSWORD=your_password
mvn spring-boot:run
```

- API 文档：http://localhost:8080/swagger-ui.html
- APK 下载：http://localhost:8080/v10.apk

## API 概览

| 方法 | 路径 | 认证 | 说明 |
|------|------|------|------|
| POST | `/api/v1/auth/register` | 无 | 注册（multipart，含头像） |
| POST | `/api/v1/auth/login` | 无 | 登录，返回 JWT |
| GET | `/api/v1/users/me` | Bearer | 当前用户信息 |
| GET | `/api/v1/users?page=1&size=20` | Bearer | 用户列表 |
| POST | `/api/v1/topics` | Bearer | 发布话题（multipart） |
| GET | `/api/v1/topics` | Bearer | 话题列表 |
| POST | `/api/v1/sports` | Bearer | 上传运动轨迹（JSON） |
| GET | `/api/v1/sports` | Bearer | 运动记录列表 |
| GET | `/api/v1/app/update` | 无 | APK 更新检查 |

### 认证方式

```bash
# 登录
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"tom","password":"123456"}'

# 后续请求携带 Token
curl http://localhost:8080/api/v1/users/me \
  -H 'Authorization: Bearer <token>'
```

### 运动轨迹请求示例

```json
POST /api/v1/sports
{
  "sportType": "run",
  "traces": [
    { "sportTime": 0.0, "latitude": 39.9, "longitude": 116.4 },
    { "sportTime": 5.2, "latitude": 39.91, "longitude": 116.41 }
  ]
}
```

### 统一响应格式

```json
{
  "code": 0,
  "message": "成功",
  "data": { ... }
}
```

## 升级记录（v3.0）

- 下线全部 `.jsp` 旧路径
- 新增 `/api/v1/` REST API
- JWT 替代每次请求传 md5password
- 密码改为服务端 BCrypt 哈希
- 运动轨迹改为 JSON 数组
- Swagger 替代 apiHelp.html
- 移除 `/allRunServer` context-path
