package com.henry.dao;

import com.henry.model.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


@Singleton
public class UserDaoImpl implements  UserDao {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("menuItems");

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            list = em.createQuery("from User").getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }

    @Override
    public Boolean save(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
        return true;
    }
}
