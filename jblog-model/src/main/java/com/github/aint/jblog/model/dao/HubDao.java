package com.github.aint.jblog.model.dao;

import com.github.aint.jblog.model.dao.hibernate.HubHibernateDao;
import com.github.aint.jblog.model.entity.Hub;

/**
 * This interface represents persistence methods for {@link Hub} objects.
 * 
 * @author Olexandr Tyshkovets
 * @see GenericDao
 * @see HubHibernateDao
 */
public interface HubDao extends GenericDao<Hub> {
}
