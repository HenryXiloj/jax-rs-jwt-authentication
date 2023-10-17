package com.henry.config;

import com.henry.resource.UserResource;
import com.henry.security.JwtResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class JerseyApplicationConfig extends Application {
    // Add resources, providers, and other configurations here

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        // Add your Jersey resources (REST endpoints) here
        classes.add(UserResource.class);
        classes.add(JwtResource.class);

        // Add other providers, filters, or features if needed
        // classes.add(MyProvider.class);
        // classes.add(MyFilter.class);

        return classes;
    }
}
