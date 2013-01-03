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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.aint.jblog.model.dao.UserDao;
import com.github.aint.jblog.model.entity.Language;
import com.github.aint.jblog.model.entity.Role;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.model.entity.VoiceValue;
import com.github.aint.jblog.service.data.UserService;
import com.github.aint.jblog.service.exception.data.DuplicateEmailException;
import com.github.aint.jblog.service.exception.data.DuplicateUserNameException;
import com.github.aint.jblog.service.exception.data.UserNotFoundException;
import com.github.aint.jblog.service.exception.data.WrongPasswordException;
import com.github.aint.jblog.service.exception.other.UserRankConfigurationException;
import com.github.aint.jblog.service.exception.security.AccessException;
import com.github.aint.jblog.service.mail.MailService;
import com.github.aint.jblog.service.other.UserRankConfiguration;
import com.github.aint.jblog.service.util.StringUtil;

/**
 * This class implementing the {@code UserService} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see UserService
 */
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final long ONE_DAY = 1000 * 60 * 60 * 24;
    private final UserDao userDao;

    /**
     * Constructs a {@code UserServiceImpl} with the given {@code userDao}.
     * 
     * @param userDao
     *            the object, which necessary for operations with a data source
     * @see UserDao
     */
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User activateUser(String uuid) throws UserNotFoundException {
        User user = userDao.getByUuid(uuid);
        if (user == null) {
            throw new UserNotFoundException("The user with the given uuid: " + uuid + " was not found");
        }
        user.setActivated(true);
        logger.debug("The user {} was activated", user.getUserName());
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNonActivatedUsers() {
        Date currentDate = new Date();
        for (User user : userDao.getNonActivatedUsers()) {
            if (currentDate.getTime() - user.getRegistrationDate().getTime() > ONE_DAY) {
                userDao.delete(user);
                logger.debug("Removes the user who has been registered more day back: {}", user);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getByUserName(String userName) throws UserNotFoundException {
        User user = userDao.getByUserName(userName);
        if (user == null) {
            throw new UserNotFoundException("The user with the given userName: " + userName + " was not found");
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User login(String userName, String password) throws UserNotFoundException, WrongPasswordException {
        User user = getByUserName(userName);
        if (!user.getPassword().equals(String.valueOf((password + user.getSalt()).hashCode()))) {
            throw new WrongPasswordException();
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User registerUser(User user, MailService mailService) throws DuplicateUserNameException,
            DuplicateEmailException {

        if (userDao.getByUserName(user.getUserName()) != null) {
            throw new DuplicateUserNameException(user.getUserName());
        }
        if (userDao.getByEmail(user.getEmail()) != null) {
            throw new DuplicateEmailException(user.getEmail());
        }

        user.setActivated(false);
        user.setBanExpirationDate(null);
        user.setBanReason(null);
        user.setRank(User.DEFAULT_RANK);
        user.setRating(0);
        user.setRegistrationDate(new Date());
        user.setRole(Role.USER);
        user.setSalt(StringUtil.generateSalt());
        user.setPassword(String.valueOf((user.getPassword() + user.getSalt()).hashCode()));
        userDao.save(user);

        mailService.sendAccountActivationMail(user);

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateRating(User user, VoiceValue voiceValue) {
        user.setRating(VoiceValue.NEGATIVE.equals(voiceValue) ? user.getRating() - 1 : user.getRating() + 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateRank(User user, UserRankConfiguration userRankConfig) {
        String oldRank = user.getRank();
        try {
            user.setRank(userRankConfig.getRank(user.getRating()));
        } catch (UserRankConfigurationException e) {
            logger.warn("Falied to update the rank of the user " + user.getUserName(), e);
        }

        return user.getRank().equals(oldRank) ? false : true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateLastLoginTime(User user) {
        user.setLastLoginTime(new Date());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLanguage(User user, Language language) {
        user.setLanguage(language);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void banUser(User caller, User user, int days, String reason) throws AccessException {
        checkAccess(caller);
        if (days <= 0) {
            throw new IllegalArgumentException("day can't be negative or zero");
        }

        Date expirationDate = new Date();
        expirationDate.setTime(new Date().getTime() + days * ONE_DAY);
        user.setBanExpirationDate(expirationDate);
        user.setBanReason(reason);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbanUser(User caller, User user) throws AccessException {
        checkAccess(caller);

        user.setBanExpirationDate(null);
        user.setBanReason(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUserBanned(User user) {
        long expirationDate = user.getBanExpirationDate() == null ? 0 : user.getBanExpirationDate().getTime();
        return expirationDate > new Date().getTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User get(Long id) throws UserNotFoundException {
        User user = userDao.get(id);
        if (user == null) {
            throw new UserNotFoundException("The user with the given id: " + id + " was not found");
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAllOnPage(int pageNumber, int pageSize, boolean head) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("pageNumber cant'be <= 0");
        }
        return userDao.getAllOnPage(pageNumber, pageSize, head);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getCount() {
        return userDao.getCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExist(Long id) {
        return userDao.get(id) != null;
    }

    private void checkAccess(User caller) throws AccessException {
        if (caller.getRole() != Role.ADMIN) {
            throw new AccessException("the caller can't have not ADMIN role");
        }
    }

}
