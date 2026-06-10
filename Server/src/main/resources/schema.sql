CREATE TABLE IF NOT EXISTS energy_system (
    id BIGSERIAL PRIMARY KEY,
    battery_level DOUBLE PRECISION NOT NULL ,
    low_battery_threshold DOUBLE PRECISION NOT NULL ,
    max_battery DOUBLE PRECISION NOT NULL ,
    min_battery DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS satellite_constellation (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS satellite (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,

    satellite_type VARCHAR(31) NOT NULL,

    constellation_id BIGINT,

    energy_id BIGINT UNIQUE,

    state_is_active BOOLEAN DEFAULT FALSE,
    state_status_message VARCHAR(255),

    temperature_inside DOUBLE PRECISION,
    temperature_outside DOUBLE PRECISION,

    frequency DOUBLE PRECISION,
    bandwidth DOUBLE PRECISION,

    resolution DOUBLE PRECISION,
    photos_taken INTEGER DEFAULT 0,

    CONSTRAINT fk_satellite_constellation
    FOREIGN KEY (constellation_id)
    REFERENCES satellite_constellation(id)
    ON DELETE SET NULL,

    CONSTRAINT fk_satellite_energy
    FOREIGN KEY (energy_id)
    REFERENCES energy_system(id)
    ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_satellite_type ON satellite(satellite_type);
CREATE INDEX IF NOT EXISTS idx_satellite_constellation ON satellite(constellation_id);
CREATE INDEX IF NOT EXISTS idx_satellite_active ON satellite(state_is_active);
CREATE INDEX IF NOT EXISTS idx_constellation_name ON satellite_constellation(name);
