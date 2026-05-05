-- Existing MySQL DBs: add JSON payload column for category-specific fields (safe to run once).
ALTER TABLE bookings ADD COLUMN booking_details TEXT;
