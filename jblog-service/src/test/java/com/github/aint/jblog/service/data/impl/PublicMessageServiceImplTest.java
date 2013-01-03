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
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.aint.jblog.model.dao.PublicMessageDao;
import com.github.aint.jblog.model.entity.PublicMessage;
import com.github.aint.jblog.service.data.PublicMessageService;
import com.github.aint.jblog.service.exception.data.EntityNotFoundException;
import com.github.aint.jblog.service.exception.data.PublicMessageNotFoundException;

/**
 * @author Olexandr Tyshkovets
 */
public class PublicMessageServiceImplTest {
    private static final Long PUBLIC_MESSAGE_ID = 1L;
    private PublicMessageService publicMessageService;
    @Mock
    private PublicMessageDao publicMessageDao;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
        publicMessageService = new PublicMessageServiceImpl(publicMessageDao);
    }

    /* ----- own methods ----- */

    @Test
    public void add() {
        final PublicMessage pm = getPublicMessage();
        publicMessageService.add(pm);

        assertNotNull(pm.getCreationDate());

        verify(publicMessageDao).save(pm);
    }

    /* ===== common methods ===== */

    @Test
    public void get() throws EntityNotFoundException {
        final PublicMessage expected = getPublicMessage();
        when(publicMessageDao.get(PUBLIC_MESSAGE_ID)).thenReturn(expected);

        assertEquals(publicMessageService.get(PUBLIC_MESSAGE_ID), expected);

        verify(publicMessageDao).get(PUBLIC_MESSAGE_ID);
    }

    @Test(expectedExceptions = PublicMessageNotFoundException.class)
    public void getNotFound() throws EntityNotFoundException {
        when(publicMessageDao.get(PUBLIC_MESSAGE_ID)).thenReturn(null);

        publicMessageService.get(PUBLIC_MESSAGE_ID);
    }

    @Test
    public void getAll() {
        final List<PublicMessage> expected = new ArrayList<PublicMessage>(Arrays.asList(getPublicMessage(),
                getPublicMessage(), getPublicMessage()));
        when(publicMessageDao.getAll()).thenReturn(expected);

        assertEquals(publicMessageService.getAll(), expected);

        verify(publicMessageDao).getAll();
    }

    @Test
    public void getAllOnPage() {
        final List<PublicMessage> expected = new ArrayList<PublicMessage>(Arrays.asList(getPublicMessage(),
                getPublicMessage()));
        when(publicMessageDao.getAllOnPage(1, 5, true)).thenReturn(expected);

        assertEquals(publicMessageService.getAllOnPage(1, 5, true), expected);

        verify(publicMessageDao).getAllOnPage(1, 5, true);
    }

    @Test
    public void getCount() {
        final long expected = 13L;
        when(publicMessageDao.getCount()).thenReturn(expected);

        assertEquals(publicMessageService.getCount(), expected);

        verify(publicMessageDao).getCount();
    }

    @Test
    public void isExist() {
        when(publicMessageDao.get(PUBLIC_MESSAGE_ID)).thenReturn(null);

        assertEquals(publicMessageService.isExist(PUBLIC_MESSAGE_ID), false);
    }

    private PublicMessage getPublicMessage() {
        PublicMessage pm = new PublicMessage("body", "authorName");
        pm.setId(PUBLIC_MESSAGE_ID);
        pm.setCreationDate(new Date());
        return pm;
    }

}
