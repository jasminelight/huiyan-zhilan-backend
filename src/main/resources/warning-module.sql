-- Warning module upgrade for MySQL 8.0.
-- Execute once against huiyan_db after backing up the existing tables.

CREATE TABLE IF NOT EXISTS t_warning (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    device_id VARCHAR(64) NOT NULL,
    water_level DECIMAL(10,2) NULL,
    warning_level INT NOT NULL,
    warning_desc VARCHAR(500) NULL,
    is_sent BOOLEAN NOT NULL DEFAULT FALSE,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS warning_threshold (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    warning_type VARCHAR(32) NOT NULL,
    warning_level INT NOT NULL,
    threshold DECIMAL(10,2) NOT NULL,
    unit VARCHAR(16) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    push_channels VARCHAR(255) DEFAULT 'WEB',
    updated_by VARCHAR(64),
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_warning_threshold (warning_type, warning_level)
);

INSERT IGNORE INTO warning_threshold(name, warning_type, warning_level, threshold, unit, enabled, push_channels)
VALUES ('积水警示', 'WATER_LEVEL', 1, 15, 'cm', TRUE, 'WEB'),
       ('积水危险', 'WATER_LEVEL', 2, 20, 'cm', TRUE, 'WEB'),
       ('雨量警示', 'RAINFALL', 1, 20, 'mm', TRUE, 'WEB'),
       ('雨量危险', 'RAINFALL', 2, 40, 'mm', TRUE, 'WEB');

-- Existing t_warning tables can be upgraded with the following columns.
ALTER TABLE t_warning
    ADD COLUMN monitoring_data_id BIGINT NULL,
    ADD COLUMN rainfall DECIMAL(10,2) NULL,
    ADD COLUMN warning_type VARCHAR(32) NOT NULL DEFAULT 'WATER_LEVEL',
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    ADD COLUMN push_channels VARCHAR(255) NULL,
    ADD COLUMN pushed_time TIMESTAMP NULL,
    ADD COLUMN handled_by VARCHAR(64) NULL,
    ADD COLUMN handle_remark VARCHAR(500) NULL,
    ADD COLUMN handled_time TIMESTAMP NULL;

CREATE INDEX idx_warning_status_time ON t_warning(status, create_time);
CREATE INDEX idx_warning_device_time ON t_warning(device_id, create_time);
