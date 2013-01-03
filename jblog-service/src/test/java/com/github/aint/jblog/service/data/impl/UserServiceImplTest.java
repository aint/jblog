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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.aint.jblog.model.dao.UserDao;
import com.github.aint.jblog.model.entity.Language;
import com.github.aint.jblog.model.entity.Role;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.model.entity.VoiceValue;
import com.github.aint.jblog.service.data.UserService;
import com.github.aint.jblog.service.exception.data.DuplicateEmailException;
import com.github.aint.jblog.service.exception.data.DuplicateUserNameException;
import com.github.aint.jblog.service.exception.data.EntityNotFoundException;
import com.github.aint.jblog.service.exception.data.UserNotFoundException;
import com.github.aint.jblog.service.exception.data.WrongPasswordException;
import com.github.aint.jblog.service.exception.other.UserRankConfigurationException;
import com.github.aint.jblog.service.exception.security.AccessException;
import com.github.aint.jblog.service.mail.MailService;
import com.github.aint.jblog.service.other.UserRankConfiguration;

/**
 * @author Olexandr Tyshkovets
 */
public class UserServiceImplTest {
    private static final Long USER_ID = 1L;
    private static final Language USER_LANGUAGE = Language.ENGLISH;
    private static final String USER_UUID = UUID.randomUUID().toString();
    private static final String USER_NAME = "userName";
    private static final String USER_PASSWORD = "password";
    private static final String USER_EMAIL = "user@gmail.com";
    private static final int USER_RATING = 0;
    private UserService userService;
    @Mock
    private UserDao userDao;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
        userService = new UserServiceImpl(userDao);
    }

    /* ----- own methods ----- */

    @Test
    public void activateUser() throws UserNotFoundException {
        final User user = getUser(1L, "user", false);
        when(userDao.getByUuid(USER_UUID)).thenReturn(user);

        userService.activateUser(USER_UUID);

        assertTrue(user.isActivated(), "user isn't activated");

        verify(userDao).getByUuid(USER_UUID);
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void activateUserNotFound() throws UserNotFoundException {
        when(userDao.getByUuid(USER_UUID)).thenReturn(null);

        userService.activateUser(USER_UUID);
    }

    @Test
    public void banUser() throws AccessException {
        final User user = getUser();
        final User caller = getUser();
        caller.setRole(Role.ADMIN);
        userService.banUser(caller, user, 10, "reason");

        assertEquals(user.getBanExpirationDate().compareTo(new Date()), 1);
        assertNotNull(user.getBanReason());
    }

    @Test(expectedExceptions = AccessException.class)
    public void banUserWithAccessViolation() throws AccessException {
        userService.banUser(getUser(), getUser(), 10, "reason");
    }

    @Test
    public void deleteNonActivatedUsers() {
        final User user1 = getUser(1L, "userName1", false);
        final Date date = new Date();
        date.setTime(System.currentTimeMillis() - 25 * 60 * 60 * 1000);
        user1.setRegistrationDate(date);
        final User user2 = getUser(2L, "userName2", false);
        date.setTime(System.currentTimeMillis() - 40 * 60 * 60 * 1000);
        user2.setRegistrationDate(date);
        when(userDao.getNonActivatedUsers()).thenReturn(new ArrayList<User>(Arrays.asList(user1, user2)));

        userService.deleteNonActivatedUsers();

        verify(userDao).delete(user1);
        verify(userDao).delete(user2);
    }

    @Test
    public void getByUserName() throws UserNotFoundException {
        final User expected = getUser();
        when(userDao.getByUserName(USER_NAME)).thenReturn(expected);

        assertEquals(userService.getByUserName(USER_NAME), expected);

        verify(userDao).getByUserName(USER_NAME);
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void getByUserNameNotFound() throws UserNotFoundException {
        when(userDao.getByUserName(USER_NAME)).thenReturn(null);

        assertNull(userService.getByUserName(USER_NAME));

        verify(userDao).getByUserName(USER_NAME);
    }

    @Test
    public void isUserBanned() {
        final User bannedUser = getUser();
        final Date date = new Date();
        date.setTime(System.currentTimeMillis() + 10000);
        bannedUser.setBanExpirationDate(date);

        assertTrue(userService.isUserBanned(bannedUser));
    }

    @Test
    public void login() throws UserNotFoundException, WrongPasswordException {
        final User user = getUser();
        user.setPassword(String.valueOf((USER_PASSWORD + user.getSalt()).hashCode()));

        when(userDao.getByUserName(USER_NAME)).thenReturn(user);

        assertEquals(userService.login(USER_NAME, USER_PASSWORD), user);

        verify(userDao).getByUserName(USER_NAME);
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void loginNotFound() throws UserNotFoundException, WrongPasswordException {
        when(userDao.getByUserName(USER_NAME)).thenReturn(null);

        userService.login(USER_NAME, USER_PASSWORD);
    }

    @Test(expectedExceptions = WrongPasswordException.class)
    public void loginWithWrongPassword() throws UserNotFoundException, WrongPasswordException {
        final User user = getUser();

        when(userDao.getByUserName(USER_NAME)).thenReturn(user);
        // in this place we use the original password instead of a hash of password and salt
        assertEquals(userService.login(USER_NAME, USER_PASSWORD), user);

        verify(userDao).getByUserName(USER_NAME);
    }

    @Test
    public void registerUser() throws DuplicateUserNameException, DuplicateEmailException {
        when(userDao.getByUserName(USER_NAME)).thenReturn(null);
        when(userDao.getByEmail(USER_EMAIL)).thenReturn(null);

        MailService mailService = mock(MailService.class);
        final User user = getUser();
        userService.registerUser(user, mailService);

        assertFalse(user.isActivated(), "user is activated");
        assertNull(user.getBanExpirationDate(), "banExpirationDate isn't null");
        assertNull(user.getBanReason(), "banReason isn't null");
        assertEquals(user.getRank(), User.DEFAULT_RANK, "ranks aren't equals");
        assertEquals(user.getRating(), 0, "rating isn't 0");
        assertNotNull(user.getRegistrationDate(), "registrationDate is null");
        assertEquals(user.getRole(), Role.USER, "roles aren't equals");
        assertNotNull(user.getSalt(), "salt is null");
        assertNotNull(user.getPassword(), "password is null");

        verify(mailService).sendAccountActivationMail(user);
        verify(userDao).save(user);
    }

    @Test
    public void setLanguage() {
        final Language language = Language.UKRAINIAN;
        final User expected = getUser();
        expected.setLanguage(language);

        final User actual = getUser();
        userService.setLanguage(actual, language);

        assertEquals(actual.getLanguage(), expected.getLanguage());
    }

    @Test
    public void unbanUser() throws AccessException {
        final User bannedUser = getUser();
        final Date date = new Date();
        date.setTime(System.currentTimeMillis() + 10000);
        bannedUser.setBanExpirationDate(date);

        final User caller = getUser();
        caller.setRole(Role.ADMIN);
        userService.unbanUser(caller, bannedUser);

        assertEquals(bannedUser.getBanExpirationDate(), null);
    }

    @Test(expectedExceptions = AccessException.class)
    public void unbanUserWithAccessViolation() throws AccessException {
        userService.unbanUser(getUser(), getUser());
    }

    @Test
    public void updateLastLoginTime() throws InterruptedException {
        final User user = getUser();
        userService.updateLastLoginTime(user);

        Thread.sleep(5);
        assertEquals(user.getLastLoginTime().compareTo(new Date()), -1);
    }

    @Test
    public void updateRank() throws UserRankConfigurationException {
        UserRankConfiguration userRankConfig = mock(UserRankConfiguration.class);
        final String newRank = "old fag";
        when(userRankConfig.getRank(USER_RATING)).thenReturn(newRank);

        final User user = getUser();
        userService.updateRank(user, userRankConfig);

        assertEquals(user.getRank(), newRank);

        verify(userRankConfig).getRank(USER_RATING);
    }

    @Test
    public void updateRating() {
        final User user = getUser();
        userService.updateRating(user, VoiceValue.POSITIVE);

        assertEquals(user.getRating(), USER_RATING + 1);
    }

    /* ===== common methods ===== */

    @Test
    public void get() throws EntityNotFoundException {
        final User expected = getUser();
        when(userDao.get(USER_ID)).thenReturn(expected);

        assertEquals(userService.get(USER_ID), expected);

        verify(userDao).get(USER_ID);
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void getNotFound() throws EntityNotFoundException {
        when(userDao.get(USER_ID)).thenReturn(null);

        userService.get(USER_ID);
    }

    @Test
    public void getAll() {
        final List<User> expected = new ArrayList<User>(Arrays.asList(getUser(), getUser(), getUser()));
        when(userDao.getAll()).thenReturn(expected);

        assertEquals(userService.getAll(), expected);

        verify(userDao).getAll();
    }

    @Test
    public void getAllOnPage() {
        final List<User> expected = new ArrayList<User>(Arrays.asList(getUser(), getUser()));
        when(userDao.getAllOnPage(1, 5, true)).thenReturn(expected);

        assertEquals(userService.getAllOnPage(1, 5, true), expected);

        verify(userDao).getAllOnPage(1, 5, true);
    }

    @Test
    public void getCount() {
        final long expected = 13L;
        when(userDao.getCount()).thenReturn(expected);

        assertEquals(userService.getCount(), expected);

        verify(userDao).getCount();
    }

    @Test
    public void isExist() {
        when(userDao.get(USER_ID)).thenReturn(null);

        assertEquals(userService.isExist(USER_ID), false);
    }

    private User getUser() {
        User user = new User(USER_NAME, USER_EMAIL, USER_PASSWORD);
        user.setId(USER_ID);
        user.setUuid(USER_UUID);
        user.setActivated(true);
        user.setBirthday(new Date());
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setLanguage(USER_LANGUAGE);
        user.setRank(User.DEFAULT_RANK);
        user.setRating(USER_RATING);
        user.setRegistrationDate(new Date());
        user.setSalt("salt");
        return user;
    }

    private User getUser(Long id, String userName, boolean activated) {
        User user = new User(userName, USER_EMAIL, "password");
        user.setId(id);
        user.setActivated(activated);
        user.setBirthday(new Date());
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setRank("rank");
        user.setRegistrationDate(new Date());
        user.setSalt("salt");
        return user;
    }

}
