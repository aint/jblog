/**
 * Copyright (C) 2012-2013  Olexandr Tyshkovets
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.github.aint.jblog.model.dao.hibernate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.aint.jblog.model.EntityFactory;
import com.github.aint.jblog.model.HibernateUtil;
import com.github.aint.jblog.model.dao.UserDao;
import com.github.aint.jblog.model.entity.User;

/**
 * @author Olexandr Tyshkovets
 */
public class UserHibernateDaoTest {
    private UserDao userDao;
    private Session session;

    @BeforeClass
    public void beforeClass() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        userDao = new UserHibernateDao(sessionFactory);
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }

    @AfterMethod
    public void afterMethod() {
        session.createQuery("DELETE User").executeUpdate();
    }

    /* ----- own methods ----- */

    @Test
    public void getByUserName() {
        final User user = getUser(1).get(0);
        session.save(user);

        assertEquals(userDao.getByUserName(user.getUserName()), user);
    }

    @Test
    public void getByUuid() {
        final User user = getUser(1).get(0);
        session.save(user);

        assertEquals(userDao.getByUuid(user.getUuid()), user);
    }

    @Test
    public void getByEmail() {
        final User user = getUser(1).get(0);
        session.save(user);

        assertEquals(userDao.getByEmail(user.getEmail()), user);
    }

    @Test
    public void getNonActivatedUsers() {
        final List<User> userList = getUser(10);
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            user.setActivated((i < 5) ? false : true);
            session.save(user);
        }

        assertEquals(userDao.getNonActivatedUsers(), userList.subList(0, 5));
    }

    /* ----- common methods ----- */

    @Test
    public void create() {
        final User user = getUser(1).get(0);
        userDao.save(user);
        session.evict(user);

        assertEquals(session.get(User.class, user.getId()), user);
    }

    @Test
    public void get() {
        final User user = getUser(1).get(0);
        session.save(user);

        assertEquals(userDao.get(user.getId()), user);
    }

    @Test
    public void getNotFound() {
        assertNull(userDao.get(-10000L));
    }

    @Test
    public void getAll() {
        final List<User> userList = getUser(5);
        for (User user : userList) {
            session.save(user);
        }

        assertEquals(userDao.getAll(), userList);
    }

    @Test
    public void getAllOnPage() {
        final List<User> userList = getUser(5);
        for (User user : userList) {
            session.save(user);
        }

        assertEquals(userDao.getAllOnPage(1, 3, true), userList.subList(0, 3));
    }

    @Test
    public void getCount() {
        final List<User> userList = getUser(5);
        for (User user : userList) {
            session.save(user);
        }

        assertEquals(userDao.getCount(), Long.valueOf(userList.size()));
    }

    @Test
    public void deleteById() {
        final User user = getUser(1).get(0);
        session.save(user);
        session.evict(user);

        userDao.delete(user.getId());
        assertNull(session.get(User.class, user.getId()));
    }

    @Test
    public void deleteByObject() {
        final User user = getUser(1).get(0);
        session.save(user);
        session.evict(user);

        userDao.delete(user);
        assertNull(session.get(User.class, user.getId()));
    }

    private List<User> getUser(int count) {
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < count; i++) {
            users.add(EntityFactory.getDefaultUser("user" + i, "user" + i + "@gmail.com"));
        }

        return users;
    }

}
