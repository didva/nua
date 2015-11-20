package com.dm.bookschecker.dao.hibernate;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.dm.bookschecker.dao.UserDao;
import com.dm.bookschecker.domain.model.User;

public class HibernateUserDao extends GenericDao<User> implements UserDao {

    @Override
    public Class<User> obtainClass() {
        return User.class;
    }

    @Override
    @Transactional
    public User find(String name) {
        return getSingleFirstResult(createCriteria().add(Restrictions.eq("username", name)));
    }
}
