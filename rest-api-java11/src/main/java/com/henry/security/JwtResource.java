package com.henry.security;

import com.henry.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.persistence.*;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.Date;

@Path("/jwt")
public class JwtResource {

    @Context
    private ServletContext servletContext;

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("menuItems");

    public String generateJwtToken(String username) {

        // Retrieve the SECRET_KEY from the ServletContext
        Key SECRET_KEY = (Key) servletContext.getAttribute("SECRET_KEY");

        // Generate JWT token with username as the subject
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + 3600000)) // Token valid for 1 hour
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();

    }

    @POST
    @Path("/generate")
    @Consumes("application/json")
    public Response generateJwtTokenWithPayload(String payload) {

        // Retrieve the SECRET_KEY from the ServletContext
        Key SECRET_KEY = (Key) servletContext.getAttribute("SECRET_KEY");
        // Assuming payload is a JSON object containing necessary information for the token
        // Parse the payload and generate the token
        long currentTimeMillis = System.currentTimeMillis();
        String jwtToken = Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + 3600000)) // Token valid for 1 hour
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        return Response.ok(jwtToken).build();
    }

    @POST
    @Path("/validate")
    @Consumes("text/plain")
    public Response validateJwtToken(String token) {
        try {
            // Retrieve the SECRET_KEY from the ServletContext
            Key SECRET_KEY = (Key) servletContext.getAttribute("SECRET_KEY");
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return Response.ok("Token is valid").build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(UserCredentials credentials) {
        // Check credentials against the database
        if (authenticate(credentials.getUsername(), credentials.getPassword())) {
            // If authentication is successful, generate a JWT token
            String jwtToken = generateJwtToken(credentials.getUsername());
            return Response.ok(jwtToken).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid username or password").build();
        }
    }

    private boolean authenticate(String username, String password) {
        // Implement logic to authenticate against the database
        // Return true if the username and password are valid, false otherwise
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            TypedQuery<User> query = entityManager
                    .createQuery(
                            "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            query.getSingleResult(); // If no result found, throws NoResultException
            return true;
        } catch (NoResultException e) {

            return false;
        }
    }
}
