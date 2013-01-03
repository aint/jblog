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
package com.github.aint.jblog.web.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
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
import com.github.aint.jblog.web.constant.CookieConstants;
import com.github.aint.jblog.web.constant.SessionConstant;

/**
 * This filter try to authorize user.
 * 
 * @author Olexandr Tyshkovets
 */
@WebFilter("/AuthorizationFilter")
public class AuthorizationFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getServletPath();
        if (path.contains(".")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(true);
        Cookie[] cookies = httpRequest.getCookies();
        if (session.getAttribute(SessionConstant.USER_NAME_SESSION_ATTRIBUTE) == null && cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CookieConstants.AUTHENTICATION_COOKIE_NAME) &&
                        URLDecoder.decode(cookie.getValue(), "UTF-8") != null) {
                    UserService userService = new UserServiceImpl(
                            new UserHibernateDao(HibernateUtil.getSessionFactory()));
                    userService.deleteNonActivatedUsers();
                    User user = null;
                    try {
                        user = userService.getByUserName(URLDecoder.decode(cookie.getValue(), "UTF-8"));
                    } catch (NumberFormatException e) {
                        logger.error("Some error occured in reading user's id from cookie {} {}",
                                e.getClass(), e.getMessage());
                        httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    } catch (UserNotFoundException e) {
                        logger.error("The user not found", e);
                        httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                    userService.updateLastLoginTime(user);
                    session.setAttribute(SessionConstant.USER_NAME_SESSION_ATTRIBUTE, user.getUserName());

                    Language language = (Language) session
                            .getAttribute(SessionConstant.USER_LANGUAGE_SESSION_ATTRIBUTE);
                    if (language != null && user.getLanguage() != language) {
                        userService.setLanguage(user, language);
                    } else {
                        session.setAttribute(SessionConstant.USER_LANGUAGE_SESSION_ATTRIBUTE, user.getLanguage());
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
    }

}
