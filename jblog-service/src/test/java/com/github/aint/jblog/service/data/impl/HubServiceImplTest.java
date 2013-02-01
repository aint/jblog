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
package com.github.aint.jblog.service.data.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.aint.jblog.model.dao.HubDao;
import com.github.aint.jblog.model.entity.Hub;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.data.HubService;
import com.github.aint.jblog.service.exception.data.DuplicateHubNameException;
import com.github.aint.jblog.service.exception.data.EntityNotFoundException;
import com.github.aint.jblog.service.exception.data.HubNotFoundException;

/**
 * @author Olexandr Tyshkovets
 */
public class HubServiceImplTest {
    private static final Long HUB_ID = 1L;
    private static final User HUB_AUTHOR = new User("userName", "user@gmail.com", "password");
    private HubService hubService;
    @Mock
    private HubDao hubDao;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
        hubService = new HubServiceImpl(hubDao);
    }

    /* ----- own methods ----- */

    @Test
    public void add() throws DuplicateHubNameException {
        final Hub hub = getHub();
        hubService.add(hub, HUB_AUTHOR);

        assertNotNull(hub.getAuthor(), "hub's author is null");
        assertEquals(hub.getRating(), 0, "hub's rating isn't 0");

        verify(hubDao).save(hub);
    }

    @Test(expectedExceptions = DuplicateHubNameException.class)
    public void addWithDuplication() throws DuplicateHubNameException {
        final Hub hub = getHub();
        when(hubDao.getByHubName(hub.getName())).thenReturn(hub);

        hubService.add(hub, HUB_AUTHOR);
    }

    @Test
    public void getByHubName() throws HubNotFoundException {
        final Hub expected = getHub();
        final String hubName = "hubName";
        when(hubDao.getByHubName(hubName)).thenReturn(expected);

        assertEquals(hubService.getByHubName(hubName), expected);
        verify(hubDao).getByHubName(hubName);
    }

    @Test(expectedExceptions = HubNotFoundException.class)
    public void getByHubNameNotFound() throws HubNotFoundException {
        final String hubName = "hubName";
        when(hubDao.getByHubName(hubName)).thenReturn(null);

        hubService.getByHubName(hubName);
    }

    /* ----- common methods ----- */

    @Test
    public void get() throws EntityNotFoundException {
        final Hub expected = getHub();
        when(hubDao.get(HUB_ID)).thenReturn(expected);

        assertEquals(hubService.get(HUB_ID), expected);
        verify(hubDao).get(HUB_ID);
    }

    @Test(expectedExceptions = HubNotFoundException.class)
    public void getNotFound() throws EntityNotFoundException {
        when(hubDao.get(HUB_ID)).thenReturn(null);

        hubService.get(HUB_ID);
    }

    @Test
    public void getAll() {
        final List<Hub> expected = new ArrayList<Hub>(Arrays.asList(getHub(), getHub(), getHub()));
        when(hubDao.getAll()).thenReturn(expected);

        assertEquals(hubService.getAll(), expected);
        verify(hubDao).getAll();
    }

    @Test
    public void getAllOnPage() {
        final List<Hub> expected = new ArrayList<Hub>(Arrays.asList(getHub(), getHub(), getHub()));
        when(hubDao.getAllOnPage(1, 5, true)).thenReturn(expected);

        assertEquals(hubService.getAllOnPage(1, 5, true), expected);
        verify(hubDao).getAllOnPage(1, 5, true);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getAllOnPageFail() {
        hubService.getAllOnPage(-1, 5, true);
    }

    @Test
    public void getCount() {
        final long expected = 13L;
        when(hubDao.getCount()).thenReturn(expected);

        assertEquals(hubService.getCount(), expected);
        verify(hubDao).getCount();
    }

    @Test
    public void isExist() {
        when(hubDao.get(HUB_ID)).thenReturn(getHub());
        assertTrue(hubService.isExist(HUB_ID));
    }

    @Test
    public void isExistNotFound() {
        when(hubDao.get(HUB_ID)).thenReturn(null);
        assertFalse(hubService.isExist(HUB_ID));
    }

    private Hub getHub() {
        Hub hub = new Hub("hubName", "hubDescription", true, HUB_AUTHOR);
        hub.setId(HUB_ID);
        return hub;
    }

}
