package com.henry.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.ServletContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.security.Key;

@Provider
@PreMatching
public class JwtTokenValidationInterceptor implements ContainerRequestFilter {

    @Context
    private ServletContext servletContext;


    @Override
    public void filter(ContainerRequestContext requestContext) {

        UriInfo info = requestContext.getUriInfo();
        if (info.getPath().contains("jwt")) {
            return;
        }

        // Retrieve the Authorization header from the request
        String token = requestContext.getHeaderString("Authorization");

        // Retrieve the SECRET_KEY from the ServletContext
        Key SECRET_KEY = (Key) servletContext.getAttribute("SECRET_KEY");

        // Check if the Authorization header is present
        if (token == null || !token.startsWith("Bearer ")) {
            throw new WebApplicationException("Missing or invalid token", 401);
        }

        // Extract the token from the Authorization header
        String jwtToken = token.substring(7); // Remove "Bearer " prefix

        // Validate the JWT token using the SECRET_KEY
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken).getBody();
            String username = claims.getSubject();

            // Set the username as a request property for the resource method

            requestContext.setProperty("username", username);
        } catch (Exception e) {
            throw new WebApplicationException("Invalid token", 401);
        }
    }
}

