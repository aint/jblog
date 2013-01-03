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
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.web.constant.ConstantHolder;
import com.github.aint.jblog.web.constant.CookieConstants;
import com.github.aint.jblog.web.constant.SessionConstant;

/**
 * This servlet changes a user's locale.
 * 
 * @author Olexandr Tyshkovets
 */
@WebServlet("/change-locale")
public class ChangeLocale extends HttpServlet {
    private static final long serialVersionUID = -1679234593167545777L;
    private static final Logger logger = LoggerFactory.getLogger(ChangeLocale.class);
    private static final String INDEX_SERVLET_URL_PATTERN = ConstantHolder.MAPPING.getProperty("mapping.servlet.index");

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String languageCode = request.getParameter("ln");
        Language language = Language.lookUp(languageCode);
        if (language == null) {
            logger.warn("The language was not found by the given language code {}", languageCode);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        HttpSession session = request.getSession(true);
        String userName = (String) session.getAttribute(SessionConstant.USER_NAME_SESSION_ATTRIBUTE);
        if (userName != null) {
            User user = null;
            UserService userService = new UserServiceImpl(new UserHibernateDao(HibernateUtil.getSessionFactory()));
            try {
                user = userService.getByUserName(userName);
            } catch (UserNotFoundException e) {
                logger.error("The user not found", e);
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            userService.setLanguage(user, language);
        }
        // sets cookie for a user's all roles, even for a guest
        Cookie cookie = new Cookie(CookieConstants.USER_LANGUAGE_CODE_COOKIE_NAME, language.getLanguageCode());
        cookie.setMaxAge(CookieConstants.USER_LANGUAGE_CODE_COOKIE_LIFETIME);
        cookie.setPath("/");
        response.addCookie(cookie);

        session.setAttribute(SessionConstant.USER_LANGUAGE_SESSION_ATTRIBUTE, language);

        logger.info("The user changed language to {}", language);

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
