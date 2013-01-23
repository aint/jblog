package com.github.aint.jblog.model.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.github.aint.jblog.model.dao.HubDao;
import com.github.aint.jblog.model.entity.Hub;

/**
 * This class is hibernate implementation of the {@code HubDao} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see HubDao
 */
public class HubHibernateDao implements HubDao {
    private final SessionFactory sessionFactory;

    /**
     * Constructs a {@code HubHibernateDao} with the given {@code sessionFactory}.
     * 
     * @param sessionFactory
     *            hibernate SessionFactory
     */
    public HubHibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Hub entity) {
        getSession().save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hub get(Long id) {
        return (Hub) getSession().get(Hub.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Hub> getAll() {
        return getSession()
                .getNamedQuery("Hub.getAll")
                .list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Hub> getAllOnPage(int pageNumber, int pageSize, boolean head) {
        return getSession()
                .getNamedQuery(head == true ? "Hub.getAllOnPageAsc" : "Hub.getAllOnPageDesc")
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getCount() {
        return (Long) getSession()
                .getNamedQuery("Hub.getCount")
                .iterate()
                .next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        getSession()
                .getNamedQuery("Hub.deleteById")
                .setLong(0, id)
                .executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Hub entity) {
        getSession().delete(entity);
    }

    /**
     * @return the current hibernate session
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
