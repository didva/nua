package com.dm.bookschecker.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.dm.bookschecker.dao.InvalidPageDao;
import com.dm.bookschecker.domain.model.InvalidPage;

public class HibernateInvalidPageDao extends GenericDao<InvalidPage> implements InvalidPageDao {

    @Override
    public Class<InvalidPage> obtainClass() {
        return InvalidPage.class;
    }

    @Override
    public List<InvalidPage> findByBookId(long bookId) {
        return createCriteria().add(Restrictions.eq("bookId", bookId)).list();
    }

}
