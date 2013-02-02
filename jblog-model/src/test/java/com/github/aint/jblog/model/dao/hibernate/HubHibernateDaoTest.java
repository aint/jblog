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

import com.github.aint.jblog.model.dao.HubDao;
import com.github.aint.jblog.model.entity.Comment;
import com.github.aint.jblog.model.entity.Hub;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.model.util.EntityFactory;
import com.github.aint.jblog.model.util.HibernateUtil;

/**
 * @author Olexandr Tyshkovets
 */
public class HubHibernateDaoTest {
    private HubDao hubDao;
    private Session session;

    @BeforeClass
    public void beforeClass() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        hubDao = new HubHibernateDao(sessionFactory);
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }

    @AfterMethod
    public void afterMethod() {
        session.createQuery("DELETE Article").executeUpdate();
        session.createQuery("DELETE Hub").executeUpdate();
        session.createQuery("DELETE User").executeUpdate();
    }

    /* ----- own methods ----- */

    @Test
    public void getByHubName() {
        final Hub hub = getHub(1).get(0);
        session.save(hub);

        assertEquals(hubDao.getByHubName(hub.getName()), hub);
    }

    @Test
    public void getByHubNameNotFound() {
        assertNull(hubDao.getByHubName(""));
    }

    @Test
    public void getAllPublicHub() {
        final List<Hub> hubs = getHub(5);
        for (int i = 0; i < hubs.size(); i++) {
            Hub hub = hubs.get(i);
            hub.setPersonal(i < 3 ? false : true);
            session.save(hub);
        }

        assertEquals(hubDao.getAllPublicHubs(), hubs.subList(0, 3));
    }

    /* ----- common methods ----- */

    @Test
    public void save() {
        final Hub hub = getHub(1).get(0);
        hubDao.save(hub);
        session.evict(hub);

        assertEquals(session.get(Hub.class, hub.getId()), hub);
    }

    @Test
    public void get() {
        final Hub hub = getHub(1).get(0);
        session.save(hub);

        assertEquals(hubDao.get(hub.getId()), hub);
    }

    @Test
    public void getNotFound() {
        assertNull(hubDao.get(-10000L));
    }

    @Test
    public void getAll() {
        final List<Hub> hubs = getHub(5);
        for (Hub hub : hubs) {
            session.save(hub);
        }

        assertEquals(hubDao.getAll(), hubs);
    }

    @Test
    public void getAllOnPage() {
        final List<Hub> hubs = getHub(5);
        for (Hub hub : hubs) {
            session.save(hub);
        }

        assertEquals(hubDao.getAllOnPage(1, 3, true), hubs.subList(0, 3));
    }

    @Test
    public void getCount() {
        final List<Hub> hubs = getHub(5);
        for (Hub hub : hubs) {
            session.save(hub);
        }

        assertEquals(hubDao.getCount(), Long.valueOf(hubs.size()));
    }

    @Test
    public void deleteById() {
        final Hub hub = getHub(1).get(0);
        session.save(hub);
        session.evict(hub);

        hubDao.delete(hub.getId());
        assertNull(session.get(Comment.class, hub.getId()));
    }

    @Test
    public void deleteByObject() {
        final Hub hub = getHub(1).get(0);
        session.save(hub);
        session.evict(hub);

        hubDao.delete(hub);
        assertNull(session.get(Comment.class, hub.getId()));
    }

    private List<Hub> getHub(int count) {
        List<Hub> hubs = new ArrayList<Hub>();
        for (int i = 0; i < count; i++) {
            User author = EntityFactory.getDefaultUser("author" + i, "author" + i + "@gmail.com");
            session.save(author);
            hubs.add(EntityFactory.getDefaultHub("name" + i, author));
        }
        return hubs;
    }

}
