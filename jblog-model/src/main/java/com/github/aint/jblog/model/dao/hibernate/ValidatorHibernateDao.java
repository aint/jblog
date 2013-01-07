package com.github.aint.jblog.model.dao.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.github.aint.jblog.model.dao.ValidatorDao;
import com.github.aint.jblog.model.entity.Entity;

/**
 * This class is hibernate implementation of the {@code ValidatorDao} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see ValidatorDao
 */
public class ValidatorHibernateDao implements ValidatorDao {
    private SessionFactory sessionFactory;

    /**
     * Constructs a {@code ValidatorHibernateDao} with the given {@code sessionFactory}.
     * 
     * @param sessionFactory
     *            hibernate SessionFactory
     */
    public ValidatorHibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean isExist(Class<? extends Entity> entity, String field, T value) {
        return !sessionFactory
                .getCurrentSession()
                .createCriteria(entity)
                .add(Restrictions.eq(field, value))
                .list()
                .isEmpty();
    }

}
