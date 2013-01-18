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

import java.util.Date;
import java.util.List;

import com.github.aint.jblog.model.dao.AnonymousMessageDao;
import com.github.aint.jblog.model.entity.AnonymousMessage;
import com.github.aint.jblog.service.data.AnonymousMessageService;
import com.github.aint.jblog.service.exception.data.AnonymousMessageNotFoundException;

/**
 * This class implementing the {@code AnonymousMessageService} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see AnonymousMessageService
 */
public class AnonymousMessageServiceImpl implements AnonymousMessageService {
    private final AnonymousMessageDao anonymousMessageDao;

    /**
     * Constructs a {@code AnonymousMessageServiceImpl} with the given {@code anonymousMessageDao}.
     * 
     * @param anonymousMessageDao
     *            the object, which necessary for operations with a data source
     * @see AnonymousMessageDao
     */
    public AnonymousMessageServiceImpl(AnonymousMessageDao anonymousMessageDao) {
        this.anonymousMessageDao = anonymousMessageDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnonymousMessage add(AnonymousMessage anonymousMessage) {
        anonymousMessage.setCreationDate(new Date());
        anonymousMessageDao.save(anonymousMessage);

        return anonymousMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnonymousMessage get(Long id) throws AnonymousMessageNotFoundException {
        AnonymousMessage anonymousMessage = anonymousMessageDao.get(id);
        if (anonymousMessage == null) {
            throw new AnonymousMessageNotFoundException("The anonymous message with the given id: " + id
                    + " was not found");
        }
        return anonymousMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnonymousMessage> getAll() {
        return anonymousMessageDao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnonymousMessage> getAllOnPage(int pageNumber, int pageSize, boolean head) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("pageNumber cant'be <= 0");
        }

        return anonymousMessageDao.getAllOnPage(pageNumber, pageSize, head);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getCount() {
        return anonymousMessageDao.getCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExist(Long id) {
        return anonymousMessageDao.get(id) != null;
    }

}
