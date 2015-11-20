package com.dm.bookschecker.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.dm.bookschecker.domain.dto.OrderType;

/**
 * Generic Dao that should be extended by all DAO interfaces that want simple CRUD operations implemented automatically
 *
 * @param <T> The type DAO manipulates
 */
public abstract class GenericDao<T> extends AbstractDao {
    /**
     * While Generics are just syntax addition to the language and exist only at compile time,
     * it is a way to obtain current class
     *
     * @return Class for the DAO
     */
    public abstract Class<T> obtainClass();

    /**
     * Saves the given entity to the database
     *
     * @param t Entity to save
     * @return Saved entity
     */
    @Transactional
    public T save(T t) {
        Session currentSession = getSessionFactory().getCurrentSession();
        currentSession.save(t);
        return t;
    }

    /**
     * Deletes the given entity from the database
     *
     * @param t Entity to delete
     */
    @Transactional
    public void delete(T t) {
        Session currentSession = getSessionFactory().getCurrentSession();
        currentSession.delete(t);
    }

    /**
     * Finds all saved entities
     *
     * @return List of entities
     */
    @Transactional
    public List<T> find() {
        Criteria criteria = createCriteria();
        return criteria.list();
    }

    /**
     * Finds all saved entities
     *
     * @return List of entities
     */
    @Transactional
    public List<T> find(String orderBy, OrderType order) {
        Order sortingOrder = getOrder(orderBy, order);
        Criteria criteria = createCriteria().addOrder(sortingOrder);
        return criteria.list();
    }

    /**
     * Finds entity by its id
     *
     * @param id id of the entity
     * @return {@link T} or null
     */
    @Transactional
    public T find(long id) {
        Criteria criteria = createCriteria().add(Restrictions.idEq(id));
        return getSingleFirstResult(criteria);
    }

    /**
     * Updates the given entity
     *
     * @param t Entity to update
     */
    @Transactional
    public void update(T t) {
        Session currentSession = getSessionFactory().getCurrentSession();
        currentSession.update(t);
    }

    /**
     * Saves or updates the given entity
     *
     * @param t Entity to save or update
     */
    @Transactional
    public void saveOrUpdate(T t) {
        Session currentSession = getSessionFactory().getCurrentSession();
        currentSession.saveOrUpdate(t);
    }

    @Transactional
    public T merge(T t) {
        return (T) getSessionFactory().getCurrentSession().merge(t);
    }

    /**
     * Creates criteria with predefined transformer that allows to exclude duplicate entities as a result of join
     *
     * @return {@link org.hibernate.Criteria} with result transformer
     */
    protected Criteria createCriteria() {
        return createCriteria(obtainClass());
    }

    /**
     * Creates criteria for specified entity class
     *
     * @param clazz {@link javax.persistence.Entity} annotated class to create criteria
     * @return {@link org.hibernate.Criteria} instance
     */
    protected Criteria createCriteria(Class<T> clazz) {
        return getSessionFactory().getCurrentSession().createCriteria(clazz).
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    }

    /**
     * Queries the criteria and takes the first result. <br/>
     * Useful for the queries that return just on result
     *
     * @param criteria {@link org.hibernate.Criteria} to query
     * @return {@link T} as the result of the query of null if the result was empty
     */
    protected T getSingleFirstResult(Criteria criteria) {
        List list = criteria.list();
        if (list.isEmpty()) {
            return null;
        }
        return (T) list.get(0);
    }

    protected Order getOrder(String orderBy, OrderType order) {
        Order sortingOrder;
        switch (order) {
            case ASC:
                sortingOrder = Order.asc(orderBy);
                break;
            case DESC:
                sortingOrder = Order.asc(orderBy);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return sortingOrder;
    }
}
