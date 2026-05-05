# OAuth2 Setup Guide for AVAILT

## Overview
To enable social login with Google, Facebook, Apple, and GitHub, you need to create OAuth applications and get credentials.

---

## 1. Google OAuth Setup

### Steps:
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project: **AVAILT**
3. Enable APIs:
   - Go to **APIs & Services** → **Library**
   - Search and enable: **Google+ API**
4. Create OAuth 2.0 Credentials:
   - Go to **APIs & Services** → **Credentials**
   - Click **+ Create Credentials** → **OAuth 2.0 Client ID**
   - Choose **Web application**
   - Add Authorized redirect URIs:
     ```
     http://localhost:8080/login/oauth2/code/google
     https://your-production-domain.com/login/oauth2/code/google
     ```
   - Download credentials (JSON)

### Credentials to copy:
- **Client ID**: `<your-google-client-id>`
- **Client Secret**: `<your-google-client-secret>`

---

## 2. Facebook OAuth Setup

### Steps:
1. Go to [Facebook Developers](https://developers.facebook.com/)
2. Create a new app: **My Apps** → **Create App** → **Consumer**
3. Add product: **Facebook Login**
4. Configure:
   - Valid OAuth Redirect URIs:
     ```
     http://localhost:8080/login/oauth2/code/facebook
     https://your-production-domain.com/login/oauth2/code/facebook
     ```
5. Get credentials from **Settings** → **Basic**

### Credentials to copy:
- **App ID**: `<your-facebook-app-id>`
- **App Secret**: `<your-facebook-app-secret>`

---

## 3. GitHub OAuth Setup

### Steps:
1. Go to [GitHub Settings → Developer settings](https://github.com/settings/developers)
2. Create **New OAuth App**
3. Fill in:
   - **Application name**: AVAILT
   - **Authorization callback URL**:
     ```
     http://localhost:8080/login/oauth2/code/github
     https://your-production-domain.com/login/oauth2/code/github
     ```
4. Generate **Client Secret**

### Credentials to copy:
- **Client ID**: `<your-github-client-id>`
- **Client Secret**: `<your-github-client-secret>`

---

## 4. Apple OAuth Setup

### Steps:
1. Go to [Apple Developer Account](https://developer.apple.com/)
2. Create **Service ID** and **Sign in with Apple** key
3. Register redirect URI:
   ```
   https://your-production-domain.com/login/oauth2/code/apple
   ```
4. Download private key

### Credentials to copy:
- **Team ID**: `<your-team-id>`
- **Client ID**: `<your-service-id>`
- **Key ID**: `<your-key-id>`
- **Private Key**: Download .p8 file

---

## Next Steps

Once you have credentials, create `.env` file in project root:

```env
# Google
OAUTH2_GOOGLE_CLIENT_ID=<your-google-client-id>
OAUTH2_GOOGLE_CLIENT_SECRET=<your-google-client-secret>

# Facebook
OAUTH2_FACEBOOK_CLIENT_ID=<your-facebook-app-id>
OAUTH2_FACEBOOK_CLIENT_SECRET=<your-facebook-app-secret>

# GitHub
OAUTH2_GITHUB_CLIENT_ID=<your-github-client-id>
OAUTH2_GITHUB_CLIENT_SECRET=<your-github-client-secret>

# Apple
OAUTH2_APPLE_TEAM_ID=<your-team-id>
OAUTH2_APPLE_CLIENT_ID=<your-service-id>
OAUTH2_APPLE_KEY_ID=<your-key-id>
OAUTH2_APPLE_PRIVATE_KEY=<your-private-key>
```

Then, use these values in `application.properties`.
