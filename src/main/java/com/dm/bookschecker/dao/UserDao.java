package com.dm.bookschecker.dao;

import java.util.List;

import com.dm.bookschecker.domain.model.User;

public interface UserDao {

    public List<User> find();

    public User find(long id);

    public User find(String name);

    public User save(User user);

    public void update(User user);

    public void delete(User user);

}
