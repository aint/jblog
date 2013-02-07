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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.github.aint.jblog.model.dao.HubDao;
import com.github.aint.jblog.model.entity.Hub;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.model.entity.VoiceValue;
import com.github.aint.jblog.service.data.HubService;
import com.github.aint.jblog.service.exception.data.DuplicateHubNameException;
import com.github.aint.jblog.service.exception.data.HubNotFoundException;

/**
 * This class implementing the {@code HubService} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see HubService
 */
public class HubServiceImpl implements HubService {
    private final HubDao hubDao;

    /**
     * Constructs a {@code HubServiceImpl} with the given {@code hubDao}.
     * 
     * @param hubDao
     *            DAO of the {@code Hub} entity
     * @see Hub
     * @see HubDao
     */
    public HubServiceImpl(HubDao hubDao) {
        this.hubDao = hubDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hub add(Hub hub, User author) throws DuplicateHubNameException {
        if (hubDao.getByHubName(hub.getName()) != null) {
            throw new DuplicateHubNameException(hub.getName());
        }
        hub.setRating(0);
        hub.setAuthor(author);
        hubDao.save(hub);

        return hub;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hub getByHubName(String name) throws HubNotFoundException {
        Hub hub = hubDao.getByHubName(name);
        if (hub == null) {
            throw new HubNotFoundException("The hub with the specified name: " + name + " wasn't found");
        }
        return hub;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getNamesOfHubsAvailableToUser(User user) {
        Set<String> publicHubs = new LinkedHashSet<String>();
        for (Hub hub : hubDao.getAllPublicHubs()) {
            publicHubs.add(hub.getName());
        }
        List<String> ownHubs = new ArrayList<String>();
        for (Hub hub : hubDao.getHubsOfUser(user.getUserName())) {
            ownHubs.add(hub.getName());
        }
        publicHubs.addAll(ownHubs);

        return publicHubs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hub updateRating(Hub hub, VoiceValue voiceValue) {
        hub.setRating(VoiceValue.NEGATIVE.equals(voiceValue) ? hub.getRating() - 1 : hub.getRating() + 1);
        return hub;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hub get(Long id) throws HubNotFoundException {
        Hub hub = hubDao.get(id);
        if (hub == null) {
            throw new HubNotFoundException("The hub with the given id: " + id + " was not found");
        }
        return hub;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Hub> getAll() {
        return hubDao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Hub> getAllOnPage(int pageNumber, int pageSize, boolean head) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("pageNumber can't be <= 0");
        }

        return hubDao.getAllOnPage(pageNumber, pageSize, head);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getCount() {
        return hubDao.getCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExist(Long id) {
        return hubDao.get(id) != null;
    }

}
