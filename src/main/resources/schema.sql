CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- `AUTO_INCREMENT` generates an auto-incrementing integer
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS chat_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    model VARCHAR(255),
    system_message VARCHAR(1000),
    user_message VARCHAR(1000),
    assistant_response VARCHAR(1000),
    temperature DOUBLE,
    timestamp TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);