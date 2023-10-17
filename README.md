# JAX-RS-JWT-AUTHENTICATION

# Run project:

mvn clean install <br>
mvn tomcat:run


# Generated token
curl --location 'http://localhost:8080/rest-api/api/jwt/login' \
--header 'Content-Type: application/json' \
--data '{
    "username": "admin",
    "password": "password123"
}
'

# Test
curl --location 'http://localhost:8080/rest-api/api/hello' \
--header 'Authorization: Bearer MY_TOKEN' \
--data ''

