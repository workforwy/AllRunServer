# AllRunServer

跑步/运动类 Android App 后端，基于 **Spring Boot 3** 的 REST API 服务。

## 技术栈

- Java 17
- Spring Boot 3.4
- Spring MVC + JdbcTemplate
- MySQL + Flyway
- 内嵌 Tomcat

## 快速开始

### 1. 准备数据库

```sql
CREATE DATABASE IF NOT EXISTS all_run DEFAULT CHARACTER SET utf8mb4;
```

### 2. 配置环境变量

```bash
export DB_USER=root
export DB_PASSWORD=your_password
export PUBLIC_BASE_URL=http://localhost:8080/allRunServer
```

### 3. 构建并运行

```bash
mvn clean package
java -jar target/allrun-server-2.0.0.jar
```

或：

```bash
mvn spring-boot:run
```

应用默认监听 `http://localhost:8080/allRunServer/`

## API 接口（兼容原 JSP 路径）

| 接口 | 方法 | 说明 |
|------|------|------|
| `/register.jsp` | POST multipart | 用户注册（含头像上传） |
| `/queryUserDetail.jsp` | POST | 查询用户详情 |
| `/queryNearbyUser.jsp` | POST | 分页查询附近用户 |
| `/addTopic.jsp` | POST multipart | 发布话题（含图片） |
| `/queryTopic.jsp` | POST | 查询话题列表 |
| `/addSportData.jsp` | POST | 上传运动 GPS 轨迹 |
| `/queryNearbySportData.jsp` | POST | 查询运动数据 |
| `/apkUpdate.jsp` | GET | APK 版本检查 |

API 文档页面：`http://localhost:8080/allRunServer/page/apiHelp.html`

## 项目结构

```
src/main/java/com/workforwy/allrun/
├── AllRunServerApplication.java
├── config/
├── controller/     # 兼容原 .jsp 路径
├── service/
├── repository/
├── entity/
└── common/
src/main/resources/
├── application.yml
├── db/migration/     # Flyway 建表脚本
└── static/           # apiHelp.html、APK 等
```

## 升级记录

- 引入 Maven + Spring Boot 3，替代 Servlet/JSP WAR 部署
- 移除 fastjson、log4j 1.x 等过时依赖
- MySQL 驱动升级至 `mysql-connector-j`
- 数据库密码改为环境变量配置
- 保留原 `.jsp` API 路径，Android 客户端无需修改
- Flyway 自动建表（`user`、`topic`、`sport`、`trace`）

## 注意事项

- 认证方式仍为客户端 MD5 哈希传参（与原 App 兼容）
- 上传文件保存在 `./uploads/` 目录（可通过 `UPLOAD_DIR` 配置）
- Context path 固定为 `/allRunServer`，与原部署路径一致
