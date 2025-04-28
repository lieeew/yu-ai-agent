create database if not exists `yu_ai_agent` default charset utf8mb4 collate utf8mb4_unicode_ci;

use `yu_ai_agent`;

CREATE TABLE IF NOT EXISTS `conversation_memory`
(
    `id`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    -- 会话 ID，长度根据实际 ID 长度调整
    `conversation_id` VARCHAR(64)     NOT NULL,
    -- 类型字段：可改为 ENUM 列出所有可能值；如果值很多可用 VARCHAR(32)
    `type`            varchar(10)     not null,
    -- 如果是 JSON 建议用 JSON 类型，否则用 TEXT/ LONGTEXT
    `memory`          text            NOT NULL,
    -- 创建与更新时间，便于按时间范围查
    `created_at`      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_delete`       tinyint                  default 0,
    PRIMARY KEY (`id`),
    INDEX idx_conv_prefix (conversation_id(10))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
