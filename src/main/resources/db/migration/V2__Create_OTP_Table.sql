-- Create OTP table
CREATE TABLE otp_codes (
    id BIGSERIAL PRIMARY KEY,
    phone_number VARCHAR(15) NOT NULL,
    otp_code VARCHAR(4) NOT NULL,
    type VARCHAR(20) NOT NULL, -- 'FORGOT_PASSWORD', 'LOGIN_OTP'
    expires_at TIMESTAMP NOT NULL,
    is_used BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX idx_otp_codes_phone_number ON otp_codes(phone_number);
CREATE INDEX idx_otp_codes_type ON otp_codes(type);
CREATE INDEX idx_otp_codes_expires_at ON otp_codes(expires_at);
CREATE INDEX idx_otp_codes_phone_type ON otp_codes(phone_number, type);

-- Update users table to make mobile_number mandatory
ALTER TABLE users ALTER COLUMN mobile_number SET NOT NULL;

-- Add index on mobile_number for faster lookups
CREATE INDEX idx_users_mobile_number ON users(mobile_number); 