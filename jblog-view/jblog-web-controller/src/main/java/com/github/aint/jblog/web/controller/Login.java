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
package com.github.aint.jblog.web.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.aint.jblog.model.dao.hibernate.UserHibernateDao;
import com.github.aint.jblog.model.entity.Language;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.data.UserService;
import com.github.aint.jblog.service.data.impl.UserServiceImpl;
import com.github.aint.jblog.service.exception.data.UserNotFoundException;
import com.github.aint.jblog.service.exception.data.WrongPasswordException;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.web.constant.ConstantHolder;
import com.github.aint.jblog.web.constant.CookieConstants;
import com.github.aint.jblog.web.constant.SessionConstant;

/**
 * This servlet sign in a user by the given user name and password.
 * 
 * @author Olexandr Tyshkovets
 */
@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 3760223875347514751L;
    private static Logger logger = LoggerFactory.getLogger(Login.class);
    private static final String LOGIN_JSP_PATH = ConstantHolder.PATH.getProperty("path.jsp.login");
    private static final String INDEX_SERVLET_URL_PATTERN = ConstantHolder.MAPPING.getProperty("mapping.servlet.index");
    private static final String LOGIN_BUTTON = "loginButton";
    private static final String USER_NAME_FIELD = "userNameField";
    private static final String USER_PASSWORD_FIELD = "userPasswordField";

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter(LOGIN_BUTTON) == null) {
            request.getRequestDispatcher(LOGIN_JSP_PATH).forward(request, response);
            return;
        }

        String userName = request.getParameter(USER_NAME_FIELD) == null ? "" :
                request.getParameter(USER_NAME_FIELD).trim();
        String userPassword = request.getParameter(USER_PASSWORD_FIELD) == null ? "" :
                request.getParameter(USER_PASSWORD_FIELD).trim();

        UserService userService = new UserServiceImpl(new UserHibernateDao(HibernateUtil.getSessionFactory()));
        userService.deleteNonActivatedUsers();
        User user = null;
        try {
            user = userService.login(userName, userPassword);
        } catch (UserNotFoundException e) {
            logger.info("The user not found", e);
            request.setAttribute("message", "login.label.failed_login");
            request.getRequestDispatcher(LOGIN_JSP_PATH).forward(request, response);
            return;
        } catch (WrongPasswordException e) {
            logger.info("The password is wrong {} for userName {}", userPassword, userName);
            request.setAttribute("message", "login.login.failed_login");
            request.getRequestDispatcher(LOGIN_JSP_PATH).forward(request, response);
            return;
        }

        if (!user.isActivated()) {
            logger.info("The user {} isn't activated", user.getUserName());
            request.setAttribute("message", "login.label.user_is_not_activated");
            request.getRequestDispatcher(LOGIN_JSP_PATH).forward(request, response);
            return;
        }

        userService.updateLastLoginTime(user);

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConstant.USER_NAME_SESSION_ATTRIBUTE, user.getUserName());
        Language language = (Language) session.getAttribute(SessionConstant.USER_LANGUAGE_SESSION_ATTRIBUTE);
        if (language != null && user.getLanguage() != language) {
            userService.setLanguage(user, language);
        } else {
            session.setAttribute(SessionConstant.USER_LANGUAGE_SESSION_ATTRIBUTE, user.getLanguage());
        }

        Cookie authenticationCookie = new Cookie(CookieConstants.AUTHENTICATION_COOKIE_NAME,
                URLEncoder.encode(user.getUserName(), "UTF-8"));
        authenticationCookie.setMaxAge(CookieConstants.AUTHENTICATION_COOKIE_LIFETIME);
        authenticationCookie.setPath("/");
        response.addCookie(authenticationCookie);
        Cookie languageCookie = new Cookie(CookieConstants.USER_LANGUAGE_CODE_COOKIE_NAME,
                user.getLanguage().getLanguageCode());
        languageCookie.setMaxAge(CookieConstants.USER_LANGUAGE_CODE_COOKIE_LIFETIME);
        languageCookie.setPath("/");
        response.addCookie(languageCookie);

        logger.info("The user {} was logined successful.", userName);

        response.sendRedirect(INDEX_SERVLET_URL_PATTERN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
