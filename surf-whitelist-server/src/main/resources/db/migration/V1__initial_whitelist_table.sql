CREATE TABLE IF NOT EXISTS whitelists
(
    id                          BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at                  TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    updated_at                  TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    uuid                        UUID                                      NOT NULL,
    blocked                     BOOLEAN      DEFAULT FALSE                NOT NULL,
    twitch_id                   VARCHAR(255)                              NULL,
    discord_id                  VARCHAR(255)                              NULL,
    added_by_discord_id         VARCHAR(255)                              NULL,
    added_by_discord_name       VARCHAR(255)                              NULL,
    added_by_discord_avatar_url VARCHAR(512)                              NULL
);
ALTER TABLE whitelists
    ADD CONSTRAINT whitelists_uuid_unique UNIQUE (uuid);
