package com.henry.dao;

import com.henry.model.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    Boolean save(User user);

}
