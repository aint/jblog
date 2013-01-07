package com.github.aint.jblog.model.dao.hibernate;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.aint.jblog.model.dao.ValidatorDao;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.model.util.EntityFactory;
import com.github.aint.jblog.model.util.HibernateUtil;

/**
 * @author Olexandr Tyshkovets
 */
public class ValidatorHibernateDaoTest {
    private ValidatorDao validatorDao;
    private Session session;

    @BeforeClass
    public void beforeClass() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        validatorDao = new ValidatorHibernateDao(sessionFactory);
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }

    @Test
    public void isExistTrue() {
        String email = "username@gmail.com";
        User user = EntityFactory.getDefaultUser("username", email);
        session.save(user);

        assertTrue(validatorDao.isExist(User.class, "email", email));
    }

    @Test
    public void isExistFalse() {
        assertFalse(validatorDao.isExist(User.class, "email", "email"));
    }

}
