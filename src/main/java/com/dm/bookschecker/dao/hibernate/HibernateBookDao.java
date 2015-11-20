package com.dm.bookschecker.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.dm.bookschecker.dao.BookDao;
import com.dm.bookschecker.domain.dto.OrderType;
import com.dm.bookschecker.domain.model.Book;

public class HibernateBookDao extends GenericDao<Book> implements BookDao {

    @Override
    public Class<Book> obtainClass() {
        return Book.class;
    }

    @Override
    @Transactional
    public List<Book> find(Boolean valid, Boolean checked, String orderBy, OrderType order, String searchText) {
        Criteria criteria = createCriteria().addOrder(getOrder(orderBy, order));
        if (valid != null) {
            criteria.add(Restrictions.eq("valid", valid));
        }
        if (checked != null) {
            criteria.add(Restrictions.eq("checked", checked));
        }
        if (searchText != null) {
            criteria.add(Restrictions.disjunction(Restrictions.ilike("author", searchText, MatchMode.ANYWHERE),
                    Restrictions.ilike("year", searchText, MatchMode.ANYWHERE),
                    Restrictions.ilike("name", searchText, MatchMode.ANYWHERE)));
        }
        return criteria.list();
    }

    @Override
    @Transactional
    public List<Book> find(int skip, int limit) {
        Criteria criteria = createCriteria().setFirstResult(skip).setMaxResults(limit);
        return criteria.list();
    }

    @Override
    @Transactional
    public int getBookCount() {
        return ((Number) createCriteria().setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }

    @Override
    public long getPagesCount() {
        return ((Number) createCriteria().setProjection(Projections.sum("pageCount")).uniqueResult()).longValue();
    }
}
