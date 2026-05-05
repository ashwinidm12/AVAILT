CREATE DATABASE IF NOT EXISTS availt;
USE availt;

CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  phone VARCHAR(255),
  password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS services (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  category VARCHAR(255),
  type VARCHAR(255),
  address VARCHAR(255),
  contact VARCHAR(255),
  rating DOUBLE,
  price DOUBLE,
  image_url VARCHAR(1024)
);

CREATE TABLE IF NOT EXISTS menus (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  service_id BIGINT,
  menu_name VARCHAR(255),
  items VARCHAR(1024),
  price DOUBLE,
  FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bookings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT,
  service_id BIGINT,
  name VARCHAR(255),
  phone VARCHAR(255),
  people_count INT,
  event_name VARCHAR(255),
  date DATE,
  venue VARCHAR(255),
  total_price DOUBLE,
  status VARCHAR(100),
  booking_details TEXT,
  FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE SET NULL,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

INSERT INTO services (name, category, type, address, contact, rating, price, image_url) VALUES
('Premium Catering Co.', 'Catering', 'Food & Beverage', 'Downtown Avenue 12, City Central', '+91 98765 43210', 4.9, 1200.0, 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&w=900&q=80'),
('Grand Banquet Hall', 'Venue Booking', 'Banquet Hall', 'Sunrise Road 5, Business Park', '+91 91234 56789', 4.8, 9500.0, 'https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=900&q=80'),
('EventSpark Management', 'Event Management', 'Corporate & Social', 'Maple Street 88, Midtown', '+91 99876 54321', 4.7, 7000.0, 'https://images.unsplash.com/photo-1521737604893-d14cc237f11d?auto=format&fit=crop&w=900&q=80'),
('FocusFrames Photography', 'Photographer', 'Event Photography', 'Cedar Lane 20, Art District', '+91 90123 45678', 4.6, 4500.0, 'https://images.unsplash.com/photo-1483985988355-763728e1935b?auto=format&fit=crop&w=900&q=80'),
('QuickCare Medical', 'Medical Services', 'Home Doctor Visit', 'Oak Avenue 32, Health Zone', '+91 93456 78901', 4.4, 1800.0, 'https://images.unsplash.com/photo-1580281657521-64cc9cb4d0c4?auto=format&fit=crop&w=900&q=80');

INSERT INTO menus (service_id, menu_name, items, price) VALUES
(1, 'Classic Buffet', 'Paneer tikka, Dal makhani, Naan, Rice, Salad', 1200.0),
(1, 'Premium Feast', 'Grilled fish, Butter chicken, Exotic salads, Dessert platter', 2200.0);
