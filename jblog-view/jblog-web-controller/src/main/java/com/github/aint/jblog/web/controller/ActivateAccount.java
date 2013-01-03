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
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.data.UserService;
import com.github.aint.jblog.service.data.impl.UserServiceImpl;
import com.github.aint.jblog.service.exception.data.UserNotFoundException;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.web.constant.ConstantHolder;
import com.github.aint.jblog.web.constant.CookieConstants;
import com.github.aint.jblog.web.constant.SessionConstant;

/**
 * This servlet activates a user's account.
 * 
 * @author Olexandr Tyshkovets
 */
@WebServlet("/activate-account")
public class ActivateAccount extends HttpServlet {
    private static final long serialVersionUID = 7259332093128201459L;
    private static final Logger logger = LoggerFactory.getLogger(ActivateAccount.class);
    private static final String ACTIVATE_ACCOUNT_JSP_PATH = ConstantHolder.PATH.getProperty("path.jsp.activateAccount");
    private static final String INDEX_SERVLET_URL_PATTERN = ConstantHolder.MAPPING.getProperty("mapping.servlet.index");

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        if (uuid == null) {
            response.sendRedirect(INDEX_SERVLET_URL_PATTERN);
            return;
        }

        UserService userService = new UserServiceImpl(new UserHibernateDao(HibernateUtil.getSessionFactory()));
        userService.deleteNonActivatedUsers();
        User user = null;
        try {
            user = userService.activateUser(uuid);
        } catch (UserNotFoundException e) {
            logger.info("The user not found", e);
            request.setAttribute("message", "activate_account.label.failed_activation");
            request.getRequestDispatcher(ACTIVATE_ACCOUNT_JSP_PATH).forward(request, response);
            return;
        }
        userService.updateLastLoginTime(user);
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConstant.USER_NAME_SESSION_ATTRIBUTE, user.getUserName());
        Cookie cookie = new Cookie(CookieConstants.AUTHENTICATION_COOKIE_NAME,
                URLEncoder.encode(user.getUserName(), "UTF-8"));
        cookie.setMaxAge(CookieConstants.AUTHENTICATION_COOKIE_LIFETIME);
        cookie.setPath("/");
        response.addCookie(cookie);
        request.setAttribute("message", "activate_account.label.successful_activation");

        request.getRequestDispatcher(ACTIVATE_ACCOUNT_JSP_PATH).forward(request, response);
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
