-- Update OTP table to use email instead of phone number
ALTER TABLE OTPCode RENAME COLUMN phone_number TO email_id;

-- Update the column type to accommodate email addresses
ALTER TABLE OTPCode ALTER COLUMN email_id TYPE VARCHAR(255);

-- Add index for better performance on email lookups
CREATE INDEX idx_otp_email_type ON OTPCode(email_id, type);

-- Drop old phone number index if it exists
DROP INDEX IF EXISTS idx_otp_phone_type; 