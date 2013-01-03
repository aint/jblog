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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.aint.jblog.model.dao.hibernate.UserHibernateDao;
import com.github.aint.jblog.model.entity.Role;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.data.UserService;
import com.github.aint.jblog.service.data.impl.UserServiceImpl;
import com.github.aint.jblog.service.exception.data.UserNotFoundException;
import com.github.aint.jblog.service.exception.security.XmlSecurityException;
import com.github.aint.jblog.service.security.SecurityService;
import com.github.aint.jblog.service.security.impl.XmlSecurityService;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.web.constant.SessionConstant;

/**
 * This filter checks user's permission to entry to some page.
 * 
 * @author Olexandr Tyshkovets
 */
@WebFilter("/security-filter")
public class SecurityFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
    private static final String SECURITY_CONFIG_XML_PATH = "/security-config.xml";
    private static final String SECURITY_CONFIG_DTD_PATH = "/security-config.dtd";
    private static final String LOGIN_SERVLET = "login";
    private static final String BAN_MSG = "You are baned";
    private SecurityService securityService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        try {
            securityService = new XmlSecurityService(getClass().getResourceAsStream(SECURITY_CONFIG_XML_PATH),
                    getClass().getResourceAsStream(SECURITY_CONFIG_DTD_PATH));
        } catch (XmlSecurityException e) {
            logger.error("Falied to configurate XmlSecurityService", e);
            throw new ServletException(e);
        }
    }

    /**
     * {@inheritDoc}
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

        User user = null;
        UserService userService = new UserServiceImpl(new UserHibernateDao(HibernateUtil.getSessionFactory()));
        String userName = (String) httpRequest.getSession(true).getAttribute(
                SessionConstant.USER_NAME_SESSION_ATTRIBUTE);
        if (userName != null) {
            try {
                user = userService.getByUserName(userName);
            } catch (UserNotFoundException e) {
                logger.error("The user not found", e);
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        if (user != null && userService.isUserBanned(user)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, BAN_MSG);
            return;
        }
        Role role = (user == null ? Role.GUEST : user.getRole());

        if (!securityService.isAuthorize(path, role)) {
            if (role == Role.GUEST) {
                request.setAttribute("message", "login.label.user_not_logined");
                httpRequest.getRequestDispatcher("/" + LOGIN_SERVLET).forward(request, response);
                return;
            } else {
                // too small priority
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
    }

}
