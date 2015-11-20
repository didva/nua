package com.dm.bookschecker.dao.hibernate;

import org.hibernate.SessionFactory;

/**
 * This is abstract class that should be implemented by all DAO implementations
 * Session factory management is implemented by Spring Transaction management mechanism
 * Particularly using {@link org.springframework.transaction.annotation.Transactional} annotation
 */
public abstract class AbstractDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
