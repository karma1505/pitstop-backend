-- Migration to create users and OTP tables with UUID (dev environment)
-- This migration creates all necessary tables from scratch

-- Create users table with UUID primary key
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    state VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    pincode VARCHAR(10) NOT NULL,
    mobile_number VARCHAR(15),
    garage_name VARCHAR(255) NOT NULL,
    address_line1 VARCHAR(255) DEFAULT '',
    address_line2 VARCHAR(255) DEFAULT '',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create OTP codes table with foreign key to users
CREATE TABLE otp_codes (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID,
    phone_number VARCHAR(15) NOT NULL,
    otp_code VARCHAR(6) NOT NULL,
    type VARCHAR(20) NOT NULL,
    is_used BOOLEAN DEFAULT FALSE,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create indexes
CREATE INDEX idx_users_id ON users(id);
CREATE INDEX idx_otp_codes_user_id ON otp_codes(user_id);

-- Add comments for documentation
COMMENT ON TABLE users IS 'Garage owners and users table with complete profile information';
COMMENT ON COLUMN users.id IS 'Unique UUID identifier for the user';
COMMENT ON COLUMN users.first_name IS 'User first name (2-50 characters)';
COMMENT ON COLUMN users.last_name IS 'User last name (2-50 characters)';
COMMENT ON COLUMN users.email IS 'User email address (unique)';
COMMENT ON COLUMN users.password IS 'Encrypted password (min 8 characters)';
COMMENT ON COLUMN users.state IS 'State where garage is located';
COMMENT ON COLUMN users.city IS 'City where garage is located';
COMMENT ON COLUMN users.pincode IS '6-digit postal code';
COMMENT ON COLUMN users.mobile_number IS '10-11 digit mobile number';
COMMENT ON COLUMN users.garage_name IS 'Name of the garage business';
COMMENT ON COLUMN users.address_line1 IS 'Primary address line';
COMMENT ON COLUMN users.address_line2 IS 'Secondary address line (optional)';
COMMENT ON COLUMN users.is_active IS 'Whether the user account is active';
COMMENT ON COLUMN users.created_at IS 'Timestamp when user was created';
COMMENT ON COLUMN users.updated_at IS 'Timestamp when user was last updated';

COMMENT ON TABLE otp_codes IS 'OTP codes for phone verification';
COMMENT ON COLUMN otp_codes.id IS 'Unique identifier for OTP record';
COMMENT ON COLUMN otp_codes.user_id IS 'Foreign key reference to users table';
COMMENT ON COLUMN otp_codes.phone_number IS 'Phone number for OTP';
COMMENT ON COLUMN otp_codes.otp_code IS '6-digit OTP code';
COMMENT ON COLUMN otp_codes.type IS 'Type of OTP (FORGOT_PASSWORD, LOGIN_OTP)';
COMMENT ON COLUMN otp_codes.is_used IS 'Whether OTP has been used';
COMMENT ON COLUMN otp_codes.expires_at IS 'When OTP expires';
COMMENT ON COLUMN otp_codes.created_at IS 'When OTP was created'; 