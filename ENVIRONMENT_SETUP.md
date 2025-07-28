# Environment Setup Guide

This guide explains how to set up environment variables for the PitStop backend application.

## Quick Setup

1. **Copy the environment template:**
   ```bash
   cp env.template .env
   ```

2. **Edit the `.env` file** and replace the placeholder values with your actual credentials.

## Environment Variables

### Required Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `GMAIL_APP_PASSWORD` | Gmail App Password for sending OTP emails | `abcd efgh ijkl mnop` |
| `DB_PASSWORD` | PostgreSQL database password | `your_db_password` |
| `JWT_SECRET` | Secret key for JWT token signing | `your-256-bit-secret-key` |

### Optional Variables (with defaults)

| Variable | Default Value | Description |
|----------|---------------|-------------|
| `DB_URL` | `jdbc:postgresql://localhost:5432/garage_db` | Database connection URL |
| `DB_USERNAME` | `garage_app` | Database username |
| `JWT_EXPIRATION` | `86400000` | JWT token expiration (24 hours) |
| `JWT_REFRESH_EXPIRATION` | `604800000` | JWT refresh token expiration (7 days) |
| `SERVER_PORT` | `8080` | Application server port |
| `SERVER_CONTEXT_PATH` | `/api/v1` | API context path |
| `LOGGING_LEVEL` | `DEBUG` | Application logging level |

## Gmail App Password Setup

1. **Enable 2-Factor Authentication** on your Google account
2. **Generate App Password:**
   - Go to [Google Account Settings](https://myaccount.google.com/)
   - Security → App passwords
   - Select "Mail" → "Other" → Name it "PitStop Backend"
   - Copy the 16-character password
3. **Add to your `.env` file:**
   ```
   GMAIL_APP_PASSWORD=your_16_character_app_password
   ```

## Loading Environment Variables

### Option 1: Using .env file
```bash
# Set environment variables from .env file
export $(cat .env | xargs)

# Run your application
java -jar your-app.jar
```

### Option 2: System Environment Variables
```bash
export GMAIL_APP_PASSWORD="your_app_password"
export DB_PASSWORD="your_db_password"
export JWT_SECRET="your_jwt_secret"
```

### Option 3: Docker Environment
```bash
docker run -e GMAIL_APP_PASSWORD=your_app_password \
           -e DB_PASSWORD=your_db_password \
           -e JWT_SECRET=your_jwt_secret \
           your-app-image
```

## Security Notes

- **Never commit `.env` files to version control**
- **Use strong, unique passwords**
- **Keep your Gmail app password secure**

## Troubleshooting

### Email Not Sending
- Verify Gmail App Password is correct
- Check that 2FA is enabled on your Google account
- Ensure the email address in `application.yml` matches your Gmail account

### Database Connection Issues
- Verify PostgreSQL is running
- Check database credentials
- Ensure database exists and is accessible

### JWT Issues
- Use a strong, unique secret key
- Ensure the secret is at least 256 bits (32 characters) 