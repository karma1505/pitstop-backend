-- Migration to update users table with complete fields from signup
-- Add missing address fields and change ID to UUID

-- First, add the new columns to existing table
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS address_line1 VARCHAR(255),
ADD COLUMN IF NOT EXISTS address_line2 VARCHAR(255);

-- Create a new table with UUID primary key
CREATE TABLE users_new (
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
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Copy data from old table to new table
INSERT INTO users_new (
    first_name, 
    last_name, 
    email, 
    password, 
    state, 
    city, 
    pincode, 
    mobile_number, 
    garage_name,
    address_line1,
    address_line2,
    is_active, 
    created_at, 
    updated_at
)
SELECT 
    first_name, 
    last_name, 
    email, 
    password, 
    state, 
    city, 
    pincode, 
    mobile_number, 
    garage_name,
    address_line1,
    address_line2,
    is_active, 
    created_at, 
    updated_at
FROM users;

-- Drop the old table
DROP TABLE users;

-- Rename the new table to users
ALTER TABLE users_new RENAME TO users;

-- Create index on UUID field for faster lookups
CREATE INDEX idx_users_id ON users(id);

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