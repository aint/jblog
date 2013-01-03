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
import com.github.aint.jblog.model.dao.PublicMessageDao;
import com.github.aint.jblog.model.entity.PublicMessage;

/**
 * @author Olexandr Tyshkovets
 */
public class PublicMessageHibernateDaoTest {
    private PublicMessageDao publicMessageDao;
    private Session session;

    @BeforeClass
    public void beforeClass() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        publicMessageDao = new PublicMessageHibernateDao(sessionFactory);
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }

    @AfterMethod
    public void afterMethod() {
        session.createQuery("DELETE PublicMessage").executeUpdate();
    }

    /* ----- common methods ----- */

    @Test
    public void create() {
        final PublicMessage pm = getPublicMessage(1).get(0);
        publicMessageDao.save(pm);
        session.evict(pm);

        assertEquals(session.get(PublicMessage.class, pm.getId()), pm);
    }

    @Test
    public void get() {
        final PublicMessage pm = getPublicMessage(1).get(0);
        session.save(pm);

        assertEquals(publicMessageDao.get(pm.getId()), pm);
    }

    @Test
    public void getNotFound() {
        assertNull(publicMessageDao.get(-10000L));
    }

    @Test
    public void getAll() {
        final List<PublicMessage> pmList = getPublicMessage(5);
        for (PublicMessage pm : pmList) {
            session.save(pm);
        }

        assertEquals(publicMessageDao.getAll(), pmList);
    }

    @Test
    public void getAllOnPage() {
        final List<PublicMessage> pmList = getPublicMessage(5);
        for (PublicMessage pm : pmList) {
            session.save(pm);
        }

        assertEquals(publicMessageDao.getAllOnPage(1, 3, true), pmList.subList(0, 3));
    }

    @Test
    public void getCount() {
        final List<PublicMessage> pmList = getPublicMessage(5);
        for (PublicMessage pm : pmList) {
            session.save(pm);
        }

        assertEquals(publicMessageDao.getCount(), Long.valueOf(pmList.size()));
    }

    @Test
    public void deleteById() {
        final PublicMessage pm = getPublicMessage(1).get(0);
        session.save(pm);
        session.evict(pm);

        publicMessageDao.delete(pm.getId());
        assertNull(session.get(PublicMessage.class, pm.getId()));
    }

    @Test
    public void deleteByObject() {
        final PublicMessage pm = getPublicMessage(1).get(0);
        session.save(pm);
        session.evict(pm);

        publicMessageDao.delete(pm);
        assertNull(session.get(PublicMessage.class, pm.getId()));
    }

    private List<PublicMessage> getPublicMessage(int count) {
        List<PublicMessage> messages = new ArrayList<PublicMessage>();
        for (int i = 0; i < count; i++) {
            messages.add(EntityFactory.getDefaultPublicMessage());
        }

        return messages;
    }

}
