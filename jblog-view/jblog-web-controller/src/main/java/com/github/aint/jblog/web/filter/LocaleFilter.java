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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.aint.jblog.model.entity.Language;
import com.github.aint.jblog.web.constant.CookieConstants;
import com.github.aint.jblog.web.constant.SessionConstant;

/**
 * This filter sets user's locale.
 * 
 * @author Olexandr Tyshkovets
 */
@WebFilter("/LocaleFilter")
public class LocaleFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LocaleFilter.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String path = httpRequest.getServletPath();
        if (path.contains(".")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(true);
        Cookie[] cookies = httpRequest.getCookies();
        if (session.getAttribute(SessionConstant.USER_LANGUAGE_SESSION_ATTRIBUTE) == null && cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CookieConstants.USER_LANGUAGE_CODE_COOKIE_NAME) &&
                        cookie.getValue() != null) {
                    Language language = Language.lookUp(cookie.getValue());
                    if (language == null) {
                        logger.warn("The language cookie contains uncorrect language code: {}", cookie.getValue());
                        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        return;
                    }
                    session.setAttribute(SessionConstant.USER_LANGUAGE_SESSION_ATTRIBUTE, language);
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
