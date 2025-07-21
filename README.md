# JAX-RS JWT Authentication

A RESTful web service implementation using JAX-RS with JWT (JSON Web Token) authentication and authorization.

## 🚀 Features

- **JWT Token Authentication**: Secure token-based authentication system
- **JAX-RS REST API**: RESTful web services using Java API for RESTful Web Services
- **Maven Build**: Easy project management and dependency handling
- **Embedded Tomcat**: Quick development setup with Maven Tomcat plugin
- **Stateless Authentication**: No server-side session management required

## 📋 Prerequisites

- Java 8 or higher
- Maven 3.6+
- An IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🛠️ Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/HenryXiloj/jax-rs-jwt-authentication.git
cd jax-rs-jwt-authentication
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn tomcat:run
```

The application will start on `http://localhost:8080/rest-api`

## 🔐 API Usage

### Authentication

#### Generate JWT Token
Request a JWT token by providing valid credentials:

```bash
curl --location 'http://localhost:8080/rest-api/api/jwt/login' \
     --header 'Content-Type: application/json' \
     --data '{
         "username": "admin",
         "password": "password123"
     }'
```

**Response:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Protected Endpoints

#### Access Protected Resource
Use the JWT token to access protected endpoints:

```bash
curl --location 'http://localhost:8080/rest-api/api/hello' \
     --header 'Authorization: Bearer YOUR_JWT_TOKEN_HERE'
```

**Response:**
```json
{
    "message": "Hello, authenticated user!"
}
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           ├── config/          # Application configuration
│   │           ├── filter/          # JWT authentication filters
│   │           ├── model/           # Data models
│   │           ├── resource/        # REST endpoints
│   │           └── util/            # Utility classes
│   └── webapp/
│       └── WEB-INF/
│           └── web.xml              # Web application configuration
└── test/                            # Unit and integration tests
```

## 🔧 Configuration

### Default Credentials
- **Username**: `admin`
- **Password**: `password123`

### JWT Settings
- **Algorithm**: HS256
- **Token Expiration**: 1 hour (configurable)
- **Secret Key**: Configured in application properties

## 🧪 Testing

### Unit Tests
```bash
mvn test
```

### Integration Tests
```bash
mvn verify
```

### Manual Testing with Postman
1. Import the provided Postman collection (if available)
2. Set the base URL to `http://localhost:8080/rest-api`
3. First, call the login endpoint to get a token
4. Use the token in the Authorization header for protected endpoints

## 📚 API Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/jwt/login` | Authenticate user and get JWT token | No |
| GET | `/api/hello` | Protected hello endpoint | Yes |

## 🛡️ Security Features

- **JWT Token Validation**: All protected endpoints validate JWT tokens
- **Password Hashing**: User passwords are securely hashed
- **Token Expiration**: Tokens have configurable expiration times
- **CORS Support**: Cross-origin resource sharing enabled
- **Input Validation**: Request data is validated before processing

## 🔍 Troubleshooting

### Common Issues

1. **Port Already in Use**
   ```bash
   # Kill process using port 8080
   lsof -ti:8080 | xargs kill -9
   ```

2. **Maven Dependencies Issues**
   ```bash
   mvn clean install -U
   ```

3. **JWT Token Expired**
   - Generate a new token using the login endpoint
   - Check token expiration settings

### Debug Mode
Run the application in debug mode:
```bash
mvn tomcat:run -Dmaven.tomcat.debug=true
```

## 🙏 Acknowledgments

- [JJWT](https://github.com/jwtk/jjwt) - JWT library for Java
- [JAX-RS](https://eclipse-ee4j.github.io/jersey/) - Java API for RESTful Web Services
- [Maven](https://maven.apache.org/) - Build automation tool
