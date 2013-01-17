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

import com.github.aint.jblog.model.dao.PublicMessageDao;
import com.github.aint.jblog.model.entity.AnonymousMessage;
import com.github.aint.jblog.model.util.EntityFactory;
import com.github.aint.jblog.model.util.HibernateUtil;

/**
 * @author Olexandr Tyshkovets
 */
public class AnonymousMessageHibernateDaoTest {
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
        session.createQuery("DELETE AnonymousMessage").executeUpdate();
    }

    /* ----- common methods ----- */

    @Test
    public void create() {
        final AnonymousMessage am = getAnonymousMessage(1).get(0);
        publicMessageDao.save(am);
        session.evict(am);

        assertEquals(session.get(AnonymousMessage.class, am.getId()), am);
    }

    @Test
    public void get() {
        final AnonymousMessage am = getAnonymousMessage(1).get(0);
        session.save(am);

        assertEquals(publicMessageDao.get(am.getId()), am);
    }

    @Test
    public void getNotFound() {
        assertNull(publicMessageDao.get(-10000L));
    }

    @Test
    public void getAll() {
        final List<AnonymousMessage> messages = getAnonymousMessage(5);
        for (AnonymousMessage am : messages) {
            session.save(am);
        }

        assertEquals(publicMessageDao.getAll(), messages);
    }

    @Test
    public void getAllOnPage() {
        final List<AnonymousMessage> messages = getAnonymousMessage(5);
        for (AnonymousMessage am : messages) {
            session.save(am);
        }

        assertEquals(publicMessageDao.getAllOnPage(1, 3, true), messages.subList(0, 3));
    }

    @Test
    public void getCount() {
        final List<AnonymousMessage> messages = getAnonymousMessage(5);
        for (AnonymousMessage am : messages) {
            session.save(am);
        }

        assertEquals(publicMessageDao.getCount(), Long.valueOf(messages.size()));
    }

    @Test
    public void deleteById() {
        final AnonymousMessage am = getAnonymousMessage(1).get(0);
        session.save(am);
        session.evict(am);

        publicMessageDao.delete(am.getId());
        assertNull(session.get(AnonymousMessage.class, am.getId()));
    }

    @Test
    public void deleteByObject() {
        final AnonymousMessage am = getAnonymousMessage(1).get(0);
        session.save(am);
        session.evict(am);

        publicMessageDao.delete(am);
        assertNull(session.get(AnonymousMessage.class, am.getId()));
    }

    private List<AnonymousMessage> getAnonymousMessage(int count) {
        List<AnonymousMessage> messages = new ArrayList<AnonymousMessage>();
        for (int i = 0; i < count; i++) {
            messages.add(EntityFactory.getDefaultAnonymousMessage());
        }

        return messages;
    }

}
