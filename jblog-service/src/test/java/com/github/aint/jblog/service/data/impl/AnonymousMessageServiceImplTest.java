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

import com.github.aint.jblog.model.dao.AnonymousMessageDao;
import com.github.aint.jblog.model.entity.AnonymousMessage;
import com.github.aint.jblog.service.data.AnonymousMessageService;
import com.github.aint.jblog.service.exception.data.EntityNotFoundException;
import com.github.aint.jblog.service.exception.data.AnonymousMessageNotFoundException;

/**
 * @author Olexandr Tyshkovets
 */
public class AnonymousMessageServiceImplTest {
    private static final Long ANONYMOUS_MESSAGE_ID = 1L;
    private AnonymousMessageService anonymousMessageService;
    @Mock
    private AnonymousMessageDao anonymousMessageDao;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
        anonymousMessageService = new AnonymousMessageServiceImpl(anonymousMessageDao);
    }

    /* ----- own methods ----- */

    @Test
    public void add() {
        final AnonymousMessage am = getAnonymousMessage();
        anonymousMessageService.add(am);

        assertNotNull(am.getCreationDate());

        verify(anonymousMessageDao).save(am);
    }

    /* ===== common methods ===== */

    @Test
    public void get() throws EntityNotFoundException {
        final AnonymousMessage expected = getAnonymousMessage();
        when(anonymousMessageDao.get(ANONYMOUS_MESSAGE_ID)).thenReturn(expected);

        assertEquals(anonymousMessageService.get(ANONYMOUS_MESSAGE_ID), expected);

        verify(anonymousMessageDao).get(ANONYMOUS_MESSAGE_ID);
    }

    @Test(expectedExceptions = AnonymousMessageNotFoundException.class)
    public void getNotFound() throws EntityNotFoundException {
        when(anonymousMessageDao.get(ANONYMOUS_MESSAGE_ID)).thenReturn(null);

        anonymousMessageService.get(ANONYMOUS_MESSAGE_ID);
    }

    @Test
    public void getAll() {
        final List<AnonymousMessage> expected = new ArrayList<AnonymousMessage>(Arrays.asList(getAnonymousMessage(),
                getAnonymousMessage(), getAnonymousMessage()));
        when(anonymousMessageDao.getAll()).thenReturn(expected);

        assertEquals(anonymousMessageService.getAll(), expected);

        verify(anonymousMessageDao).getAll();
    }

    @Test
    public void getAllOnPage() {
        final List<AnonymousMessage> expected = new ArrayList<AnonymousMessage>(Arrays.asList(getAnonymousMessage(),
                getAnonymousMessage()));
        when(anonymousMessageDao.getAllOnPage(1, 5, true)).thenReturn(expected);

        assertEquals(anonymousMessageService.getAllOnPage(1, 5, true), expected);

        verify(anonymousMessageDao).getAllOnPage(1, 5, true);
    }

    @Test
    public void getCount() {
        final long expected = 13L;
        when(anonymousMessageDao.getCount()).thenReturn(expected);

        assertEquals(anonymousMessageService.getCount(), expected);

        verify(anonymousMessageDao).getCount();
    }

    @Test
    public void isExist() {
        when(anonymousMessageDao.get(ANONYMOUS_MESSAGE_ID)).thenReturn(null);

        assertEquals(anonymousMessageService.isExist(ANONYMOUS_MESSAGE_ID), false);
    }

    private AnonymousMessage getAnonymousMessage() {
        AnonymousMessage am = new AnonymousMessage("body", "authorName");
        am.setId(ANONYMOUS_MESSAGE_ID);
        am.setCreationDate(new Date());
        return am;
    }

}
