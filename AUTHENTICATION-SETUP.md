# AVAILT Authentication System - Implementation Guide

## ✅ What's Done

Your AVAILT application now has a complete **OAuth2 + JWT authentication system** with:

1. ✅ **Login/Signup Pages** with beautiful UI
   - `login.html` - Email/password login with social buttons
   - `signup.html` - Email/password registration with social buttons

2. ✅ **Backend Authentication API**
   - `POST /api/auth/login` - Email/password login
   - `POST /api/auth/signup` - Email/password registration
   - `GET /api/auth/verify` - Verify JWT token
   - `GET /api/auth/oauth2-success` - OAuth2 callback handler
   - `POST /api/logout` - Logout

3. ✅ **JWT Token System**
   - Token generation with 24-hour expiration
   - Token validation
   - Secure password hashing with BCrypt

4. ✅ **Database Schema**
   - Users table with: id, name, email, phone, password

---

## ⚠️ What You Need To Do

### Step 1: Create OAuth2 Applications

Follow the detailed steps in `OAuth-Setup-Guide.md` to create OAuth2 credentials for:
- ✅ Google
- ✅ Facebook
- ✅ GitHub
- ✅ Apple

### Step 2: Set Environment Variables

Create a `.env` file in your project root:

```env
# Google OAuth2
OAUTH2_GOOGLE_CLIENT_ID=<your-google-client-id>
OAUTH2_GOOGLE_CLIENT_SECRET=<your-google-client-secret>

# Facebook OAuth2
OAUTH2_FACEBOOK_CLIENT_ID=<your-facebook-app-id>
OAUTH2_FACEBOOK_CLIENT_SECRET=<your-facebook-app-secret>

# GitHub OAuth2
OAUTH2_GITHUB_CLIENT_ID=<your-github-client-id>
OAUTH2_GITHUB_CLIENT_SECRET=<your-github-client-secret>

# Apple OAuth2
OAUTH2_APPLE_CLIENT_ID=<your-service-id>
OAUTH2_APPLE_CLIENT_SECRET=<your-client-secret>
OAUTH2_APPLE_TEAM_ID=<your-team-id>
OAUTH2_APPLE_KEY_ID=<your-key-id>

# JWT
JWT_SECRET=<your-secret-key-minimum-32-chars>
JWT_EXPIRATION=86400000
```

### Step 3: Run the Application

```bash
cd /home/aaaa/AVAIL
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64

# Load environment variables
export $(cat .env | xargs)

# Start the backend
mvn spring-boot:run
```

### Step 4: Test the Authentication Flow

1. **Open in browser:**
   ```
   http://localhost:8080/login.html
   ```

2. **Test Email/Password Login:**
   - Click the "Sign up here" link on login page
   - Fill signup form with: name, email, phone, password
   - After signup, you'll get a JWT token in localStorage
   - JWT token will be sent to all `/api/*` endpoints automatically

3. **Test Social Login (OAuth2):**
   - Click any social button (Google, Facebook, GitHub, Apple)
   - Follow the provider's authorization flow
   - You'll be redirected back with a JWT token

---

## 🔑 How JWT Works

### Token Generation
```javascript
// After login/signup, you get:
{
  "token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "+91XXXXX"
  }
}
```

### Using the Token
Token is automatically saved in `localStorage.authToken` and sent with every API request:

```javascript
// JavaScript example
const token = localStorage.getItem('authToken');
fetch('/api/bookings', {
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
```

### Token Validation
```bash
curl -H "Authorization: Bearer <token>" \
  http://localhost:8080/api/auth/verify
```

---

## 🔐 Security Features

✅ Passwords hashed with BCrypt (never stored in plain text)  
✅ JWT tokens expire after 24 hours  
✅ CORS configured for localhost:3000, localhost:8080, and GitHub Pages  
✅ OAuth2 with PKCE (Proof Key for Code Exchange) for extra security  
✅ Automatic user creation on first OAuth2 login  

---

## 📱 Update Frontend to Use Authentication

### 1. Add Login Check to Home Page

Edit `index.html` - add before `</body>`:
```html
<script>
  document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('authToken');
    if (!token) {
      // User not logged in, redirect to login
      // window.location.href = '/login.html';
    } else {
      // User logged in, show their name
      const user = JSON.parse(localStorage.getItem('user'));
      console.log('Welcome,', user.name);
    }
  });
</script>
```

### 2. Add Logout Button

Add to your header/navigation:
```html
<button onclick="logout()">Logout</button>

<script>
  function logout() {
    localStorage.removeItem('authToken');
    localStorage.removeItem('user');
    window.location.href = '/login.html';
  }
</script>
```

### 3. Protect API Calls

Update your JavaScript to include JWT token:
```javascript
// In js/api.js or your service files
const token = localStorage.getItem('authToken');
fetch('/api/bookings', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify(bookingData)
});
```

---

## 🚀 Deploy to Production

### 1. Update OAuth2 Redirect URIs

For each OAuth provider (Google, Facebook, etc.), add:
```
https://yourdomain.com/login/oauth2/code/google
https://yourdomain.com/login/oauth2/code/facebook
https://yourdomain.com/login/oauth2/code/github
https://yourdomain.com/login/oauth2/code/apple
```

### 2. Set Environment Variables on Production Server

```bash
export OAUTH2_GOOGLE_CLIENT_ID=...
export OAUTH2_GOOGLE_CLIENT_SECRET=...
# ... etc for all providers

export JWT_SECRET=<very-long-random-string>
export SERVER_PORT=8080

mvn spring-boot:run
```

### 3. Update CORS Origins in `OAuth2SecurityConfig.java`

```java
configuration.setAllowedOrigins(Arrays.asList(
  "http://localhost:3000",
  "http://localhost:8080",
  "https://yourdomain.com",
  "https://www.yourdomain.com"
));
```

---

## 🐛 Troubleshooting

### "Invalid OAuth credentials"
- Verify Client ID and Client Secret are correct
- Check Redirect URIs match exactly (including protocol)
- OAuth2 app must be approved/verified by provider

### "JWT token expired"
- Frontend should redirect to login page
- User can login again to get new token

### "CORS error when calling API"
- Ensure your frontend origin is in `allowedOrigins`
- Check browser console for exact error

### "User not found after OAuth2 login"
- User is auto-created on first OAuth2 login
- Check `users` table: `SELECT * FROM users;` in H2 console

---

## 📚 API Endpoints Summary

| Method | Endpoint | Auth | Purpose |
|--------|----------|------|---------|
| POST | `/api/auth/login` | ❌ | Email/password login |
| POST | `/api/auth/signup` | ❌ | Email/password signup |
| GET | `/api/auth/verify` | ✅ | Verify JWT token |
| GET | `/api/auth/oauth2-success` | ❌ | OAuth2 callback |
| POST | `/api/auth/logout` | ✅ | Logout |
| GET | `/oauth2/authorization/{provider}` | ❌ | Start OAuth2 flow |
| GET | `/login/oauth2/code/{provider}` | ❌ | OAuth2 callback (auto-handled) |

---

## ✨ Next Steps

1. **Get OAuth2 Credentials** → Follow OAuth-Setup-Guide.md
2. **Set Environment Variables** → Create `.env` file
3. **Start Backend** → `mvn spring-boot:run`
4. **Test Login** → http://localhost:8080/login.html
5. **Update Frontend** → Add auth checks to pages
6. **Deploy** → Follow production setup above

---

## 📝 File Changes Summary

**New Files:**
- `src/main/java/com/availt/security/JwtTokenProvider.java` - JWT token management
- `src/main/java/com/availt/security/OAuth2SecurityConfig.java` - OAuth2 configuration
- `src/main/java/com/availt/security/PasswordEncoderConfig.java` - Password encryption
- `src/main/resources/static/login.html` - Login page
- `src/main/resources/static/signup.html` - Signup page
- `OAuth-Setup-Guide.md` - Detailed setup instructions

**Modified Files:**
- `src/main/java/com/availt/controller/AuthController.java` - Complete rewrite with new endpoints
- `src/main/java/com/availt/model/User.java` - Added email field
- `src/main/java/com/availt/repository/UserRepository.java` - Added findByEmail method
- `src/main/java/com/availt/service/UserService.java` - Added helper methods
- `pom.xml` - Added OAuth2 and JWT dependencies
- `src/main/resources/application.properties` - Added OAuth2 config

---

Good luck! Your AVAILT app is now ready for real-world authentication. 🎉
