package com.henry.config;

import com.henry.model.User;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.security.Key;

public class SecretKeyInitializerListener implements ServletContextListener {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("menuItems");

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {

            // Initialize your SECRET_KEY
            Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

            // Store the SECRET_KEY in the ServletContext for later use
            ServletContext servletContext = sce.getServletContext();
            servletContext.setAttribute("SECRET_KEY", SECRET_KEY);

            // Add mock data
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            User user1 = new User();
            user1.setUsername("admin");
            user1.setPassword("password123");

            entityManager.persist(user1);

            User user2 = new User();
            user2.setUsername("henry");
            user2.setPassword("password123");
            entityManager.persist(user2);

            entityManager.getTransaction().commit();
            entityManager.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
