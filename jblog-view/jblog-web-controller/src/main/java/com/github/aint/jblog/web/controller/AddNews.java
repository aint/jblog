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
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.aint.jblog.model.dao.hibernate.NewsHibernateDao;
import com.github.aint.jblog.model.dao.hibernate.UserHibernateDao;
import com.github.aint.jblog.model.entity.Language;
import com.github.aint.jblog.service.data.NewsService;
import com.github.aint.jblog.service.data.UserService;
import com.github.aint.jblog.service.data.impl.NewsServiceImpl;
import com.github.aint.jblog.service.data.impl.UserServiceImpl;
import com.github.aint.jblog.service.exception.data.UserNotFoundException;
import com.github.aint.jblog.service.exception.security.AccessException;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.service.validation.Validation;
import com.github.aint.jblog.web.constant.ConstantHolder;
import com.github.aint.jblog.web.constant.SessionConstant;
import com.github.aint.jblog.web.dto.NewsDto;

/**
 * This servlet adds a news.
 * 
 * @author Olexandr Tyshkovets
 */
@WebServlet("/add-news")
public class AddNews extends HttpServlet {
    private static final long serialVersionUID = -6356926947821599871L;
    private static Logger logger = LoggerFactory.getLogger(AddNews.class);
    private static final String ADD_NEWS_JSP_PATH = ConstantHolder.PATH.getProperty("path.jsp.addNews");
    private static final String INDEX_SERVLET_URL_PATTERN = ConstantHolder.MAPPING.getProperty("mapping.servlet.index");
    private static final String ADD_NEWS_BUTTON = "addNewsButton";
    private static final String NEWS_TITLE_FIELD = "newsTitleField";
    private static final String NEWS_BODY_FIELD = "newsBodyField";
    private static final String NEWS_IS_PINNED_FIELD = "newsIsPinnedField";
    private static final String NEWS_IMPORTANCE_FIELD = "newsImportanceField";
    private NewsService newsService;
    private UserService userService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        newsService = new NewsServiceImpl(new NewsHibernateDao(HibernateUtil.getSessionFactory()));
        userService = new UserServiceImpl(new UserHibernateDao(HibernateUtil.getSessionFactory()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter(ADD_NEWS_BUTTON) == null) {
            request.getRequestDispatcher(ADD_NEWS_JSP_PATH).forward(request, response);
            return;
        }

        String newsTitle = request.getParameter(NEWS_TITLE_FIELD);
        String newsBody = request.getParameter(NEWS_BODY_FIELD);
        String newsImportance = request.getParameter(NEWS_IMPORTANCE_FIELD);

        NewsDto newsDto = new NewsDto(newsTitle, newsBody, request.getParameter(NEWS_IS_PINNED_FIELD), newsImportance);
        Language language = (Language)
                request.getSession().getAttribute(SessionConstant.USER_LANGUAGE_SESSION_ATTRIBUTE);
        Validator validator = Validation.getValidator(language.getLocale());
        Set<ConstraintViolation<NewsDto>> validationErrors = validator.validate(newsDto);
        if (!validationErrors.isEmpty()) {
            logger.debug("The news' validation error messages: {}", validationErrors);
            request.setAttribute("validationErrors", validationErrors);
            request.setAttribute(NEWS_TITLE_FIELD, newsTitle);
            request.setAttribute(NEWS_BODY_FIELD, newsBody);
            request.getRequestDispatcher(ADD_NEWS_JSP_PATH).forward(request, response);
            return;
        }

        String userName = (String) request.getSession(true).getAttribute(SessionConstant.USER_NAME_SESSION_ATTRIBUTE);
        try {
            newsService.addNews(newsDto.createNews(), userService.getByUserName(userName));
        } catch (UserNotFoundException e) {
            logger.error("The user not found", e);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        } catch (AccessException e) {
            logger.error("The user" + userName + " doesn't have permission to add a news", e);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

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
