package com.henry.resource;

import com.henry.dao.UserDaoImpl;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class UserResource {

    @Context
    private ServletContext servletContext;

    UserDaoImpl userDao = new UserDaoImpl();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello(@Context ContainerRequestContext requestContext) {
        // Retrieve the username from the request property set in the interceptor
        String username = (String) requestContext.getProperty("username");
        System.out.println("username "+ username);
        try {
            return Response.ok("Hello, " + username + "!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error").build();
        }
    }


}


