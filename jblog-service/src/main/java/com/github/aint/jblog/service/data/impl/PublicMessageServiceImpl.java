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

import com.github.aint.jblog.model.dao.PublicMessageDao;
import com.github.aint.jblog.model.entity.PublicMessage;
import com.github.aint.jblog.service.data.PublicMessageService;
import com.github.aint.jblog.service.exception.data.PublicMessageNotFoundException;

/**
 * This class implementing the {@code PublicMessageService} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see PublicMessageService
 */
public class PublicMessageServiceImpl implements PublicMessageService {
    private final PublicMessageDao publicMessageDao;

    /**
     * Constructs a {@code PublicMessageServiceImpl} with the given {@code publicMessageDao}.
     * 
     * @param publicMessageDao
     *            the object, which necessary for operations with a data source
     * @see PublicMessageDao
     */
    public PublicMessageServiceImpl(PublicMessageDao publicMessageDao) {
        this.publicMessageDao = publicMessageDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublicMessage add(PublicMessage publicMessage) {
        publicMessage.setCreationDate(new Date());
        publicMessageDao.save(publicMessage);

        return publicMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PublicMessage get(Long id) throws PublicMessageNotFoundException {
        PublicMessage publicMessage = publicMessageDao.get(id);
        if (publicMessage == null) {
            throw new PublicMessageNotFoundException("The public message with the given id: " + id + " was not found");
        }
        return publicMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PublicMessage> getAll() {
        return publicMessageDao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PublicMessage> getAllOnPage(int pageNumber, int pageSize, boolean head) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("pageNumber cant'be <= 0");
        }

        return publicMessageDao.getAllOnPage(pageNumber, pageSize, head);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getCount() {
        return publicMessageDao.getCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExist(Long id) {
        return publicMessageDao.get(id) != null;
    }

}
