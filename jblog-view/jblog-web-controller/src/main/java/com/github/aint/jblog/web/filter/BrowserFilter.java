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

import com.github.aint.jblog.service.other.Browser;
import com.github.aint.jblog.service.other.BrowserService;
import com.github.aint.jblog.service.other.impl.BrowserServiceImpl;
import com.github.aint.jblog.web.constant.ConstantHolder;
import com.github.aint.jblog.web.constant.SessionConstant;

/**
 * This filter determines users's browser.
 * 
 * @author Olexandr Tyshkovets
 */
@WebFilter("/BrowserFilter")
public class BrowserFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(BrowserFilter.class);
    private static final String MSIE_JSP = ConstantHolder.MAPPING.getProperty("mapping.jsp.msie");
    private static final String OLD_BROWSER_MSG = "Your %s %s is too old, please update your browser or install a new browser.";
    private static final String UNKNOWN_BROWSER_MSG = "Unknown browser. For correct display of a content, " +
            "please install a really good browser:";

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

        String userAgent = httpRequest.getHeader("User-Agent");
        String userName = (String) httpRequest.getSession(true)
                .getAttribute(SessionConstant.USER_NAME_SESSION_ATTRIBUTE);
        logger.info("user: {}, IP: {}, user-agent: {}", new Object[] { (userName != null ? userName : "anonym"),
                httpRequest.getRemoteAddr(), userAgent });

        BrowserService browserService = new BrowserServiceImpl(userAgent);
        String browserName = browserService.getBrowserName();
        String browserVersion = browserService.getBrowserVersion();

        logger.debug("browser name: {}, full version: {}, major version: {}",
                new Object[] { browserName, browserVersion, browserService.getBrowserMajorVersion() });

        Browser browser = Browser.lookUp(browserName);
        if (browser != null) {
            if (Browser.MSIE == browser && path.indexOf(MSIE_JSP) == -1) {
                httpRequest.getRequestDispatcher(MSIE_JSP).forward(httpRequest, httpResponse);
                return;
            }
            if (browser.getBrowserMinVersion() > browserService.getBrowserMajorVersion()) {
                httpRequest.setAttribute("BROWSER_MSG", String.format(OLD_BROWSER_MSG, browserName, browserVersion));
            }
        } else {
            httpRequest.setAttribute("BROWSER_MSG", UNKNOWN_BROWSER_MSG);
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
