CREATE TABLE IF NOT EXISTS `user` (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    nickname    VARCHAR(255),
    gender      VARCHAR(10),
    iconUrl     VARCHAR(512),
    latitude    DOUBLE DEFAULT 0,
    longitude   DOUBLE DEFAULT 0,
    intro       TEXT,
    regTime     DOUBLE
);

CREATE TABLE IF NOT EXISTS topic (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(255) NOT NULL,
    content     TEXT,
    imageUrl    VARCHAR(512),
    address     VARCHAR(512),
    latitude    DOUBLE DEFAULT 0,
    longitude   DOUBLE DEFAULT 0,
    createTime  BIGINT
);

CREATE TABLE IF NOT EXISTS sport (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(255) NOT NULL,
    sportType   VARCHAR(50),
    uuid        VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS trace (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    sportId     INT NOT NULL,
    sportTime   DOUBLE,
    latitude    DOUBLE,
    longitude   DOUBLE
);
