-- AVAILT MySQL schema aligned with JPA entities (Spring Boot + Hibernate).
-- Create DB and run this file, or rely on spring.jpa.hibernate.ddl-auto=update with an empty database.
-- Sample data: the app seeds services/menus on first run when tables are empty (DataInitializer).

CREATE DATABASE IF NOT EXISTS availt;
USE availt;

CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  UNIQUE KEY uk_users_email (email)
);

CREATE TABLE IF NOT EXISTS services (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  category VARCHAR(255) NOT NULL,
  type VARCHAR(255),
  address VARCHAR(255),
  contact VARCHAR(255),
  rating DOUBLE,
  price DOUBLE,
  image_url VARCHAR(1024),
  description TEXT,
  city VARCHAR(255),
  owner_name VARCHAR(255),
  provider_email VARCHAR(255),
  opening_hours VARCHAR(512),
  price_max DOUBLE,
  gallery_image_urls TEXT,
  community_submitted TINYINT(1) DEFAULT 0
);

CREATE TABLE IF NOT EXISTS menus (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  service_id BIGINT NOT NULL,
  menu_name VARCHAR(255) NOT NULL,
  items TEXT,
  price DOUBLE,
  image_url VARCHAR(1024),
  CONSTRAINT fk_menus_service FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bookings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  service_id BIGINT NOT NULL,
  category VARCHAR(128) NOT NULL,
  booking_data TEXT NOT NULL,
  selected_menu TEXT,
  total_price DOUBLE NOT NULL,
  status VARCHAR(32) NOT NULL,
  created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  CONSTRAINT fk_bookings_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_bookings_service FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
);
