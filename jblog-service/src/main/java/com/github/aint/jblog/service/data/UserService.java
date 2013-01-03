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
package com.github.aint.jblog.service.data;

import com.github.aint.jblog.model.entity.Language;
import com.github.aint.jblog.model.entity.Role;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.model.entity.VoiceValue;
import com.github.aint.jblog.service.data.impl.UserServiceImpl;
import com.github.aint.jblog.service.exception.data.DuplicateEmailException;
import com.github.aint.jblog.service.exception.data.DuplicateUserNameException;
import com.github.aint.jblog.service.exception.data.UserNotFoundException;
import com.github.aint.jblog.service.exception.data.WrongPasswordException;
import com.github.aint.jblog.service.exception.security.AccessException;
import com.github.aint.jblog.service.mail.MailService;
import com.github.aint.jblog.service.other.UserRankConfiguration;

/**
 * This interface represents methods to operate with a {@link User}.
 * 
 * @author Olexandr Tyshkovets
 * @see GenericEntityService
 * @see UserServiceImpl
 */
public interface UserService extends GenericEntityService<User> {
    /**
     * This method activates a {@link User} by the given {@code uuid}.
     * 
     * @param uuid
     *            a {@link User}'s immutable universally unique identifier
     * @return a activated {@link User}
     * @throws UserNotFoundException
     *             if a {@link User} with the given {@code userName} doesn't exist
     * @see java.util.UUID
     */
    User activateUser(String uuid) throws UserNotFoundException;

    /**
     * Deletes all non activated users which were registered more day back.
     * 
     * @see User
     */
    void deleteNonActivatedUsers();

    /**
     * Returns a {@link User} by the given {@code userName}.
     * 
     * @param userName
     *            the userName of the requested user
     * @return a {@link User}
     * @throws UserNotFoundException
     *             if a {@link User} with the given {@code userName} doesn't exist
     */
    User getByUserName(String userName) throws UserNotFoundException;

    /**
     * Registers a new {@link User}.
     * 
     * @param user
     *            a {@link User}, which will be registered
     * @param mailService
     *            the object, which sends an activation email message
     * @return a registered {@link User}
     * @throws DuplicateUserNameException
     *             if userName is duplicated
     * @throws DuplicateEmailException
     *             if email is duplicated
     * @see MailService
     */
    User registerUser(User user, MailService mailService) throws DuplicateUserNameException, DuplicateEmailException;

    /**
     * Attempts to login by the given {@code userName} and {@code password}.
     * 
     * @param userName
     *            a {@link User}'s userName
     * @param password
     *            a {@link User}'s password
     * @return a {@link User} if attempt was successful
     * @throws UserNotFoundException
     *             if a {@link User} with the given {@code userName} doesn't exist
     * @throws WrongPasswordException
     *             if the given {@code password} is wrong
     */
    User login(String userName, String password) throws UserNotFoundException, WrongPasswordException;

    /**
     * Updates a {@link User}'s last login time.
     * 
     * @param user
     *            a {@link User}, which must be updated
     */
    void updateLastLoginTime(User user);

    /**
     * Sets the {@code user}'s language.
     * 
     * @param user
     *            a {@link User} to which will set the {@code language}
     * @param language
     *            the {@code user}'s {@link Language}
     */
    void setLanguage(User user, Language language);

    /**
     * Updates a {@link User}'s rating.
     * 
     * @param user
     *            a {@link User} whose rating will be updated
     * @param voiceValue
     *            the positive or negative voice
     * @see VoiceValue
     * @see User#getRating()
     */
    void updateRating(User user, VoiceValue voiceValue);

    /**
     * Updates a {@link User}'s rank.
     * 
     * @param user
     *            a {@link User} whose rank will be updated
     * @param userRankConfig
     *            this object contains methods which defines a new {@link User}'s rank by its rating
     * @return {@code true} if a rank was updated; if a rank remained the same - returns {@code false}
     * @see UserRankConfiguration
     * @see User#getRank()
     */
    boolean updateRank(User user, UserRankConfiguration userRankConfig);

    /**
     * Bans the {@code user} on the given time.
     * 
     * @param caller
     *            a {@link User} which will sets a ban to the {@code user}. The {@code caller} should have {@code ADMIN}
     *            role
     * @param user
     *            a {@link User} which will be banned
     * @param days
     *            the length of the ban in days
     * @param reason
     *            the ban reason
     * @throws AccessException
     *             if the {@code caller} doesn't have permission to perform this action. The {@code caller} should have
     *             {@code ADMIN} role
     * @throws IllegalArgumentException
     *             if the {@code days} is <= 0
     * @see Role
     */
    void banUser(User caller, User user, int days, String reason) throws AccessException;

    /**
     * Unbans the {@code user}.
     * 
     * @param caller
     *            a {@link User} which will sets a ban to the {@code user}. The {@code caller} should have {@code ADMIN}
     *            role
     * @param user
     *            a {@link User} which will be unbanned
     * @throws AccessException
     *             if the {@code caller} doesn't have permission to perform this action. The {@code caller} should have
     *             {@code ADMIN} role
     * @see Role
     */
    void unbanUser(User caller, User user) throws AccessException;

    /**
     * Checks whether the {@code user} is banned.
     * 
     * @param user
     *            a {@link User} which will be checked
     * @return {@code true} if the {@code user} is banned; {@code false} otherwise
     * @see Role
     */
    boolean isUserBanned(User user);

}
