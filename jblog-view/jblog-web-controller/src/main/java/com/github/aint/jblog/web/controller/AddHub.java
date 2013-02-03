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

import com.github.aint.jblog.model.dao.hibernate.HubHibernateDao;
import com.github.aint.jblog.model.dao.hibernate.UserHibernateDao;
import com.github.aint.jblog.model.entity.Language;
import com.github.aint.jblog.service.data.HubService;
import com.github.aint.jblog.service.data.UserService;
import com.github.aint.jblog.service.data.impl.HubServiceImpl;
import com.github.aint.jblog.service.data.impl.UserServiceImpl;
import com.github.aint.jblog.service.exception.data.DuplicateHubNameException;
import com.github.aint.jblog.service.exception.data.UserNotFoundException;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.service.validation.Validation;
import com.github.aint.jblog.web.constant.ConstantHolder;
import com.github.aint.jblog.web.constant.SessionConstant;
import com.github.aint.jblog.web.dto.HubDto;

/**
 * This servlet adds a hub.
 * 
 * @author Olexandr Tyshkovets
 */
@WebServlet("/add-hub")
public class AddHub extends HttpServlet {
    private static final long serialVersionUID = 1869782172856853746L;
    private static Logger logger = LoggerFactory.getLogger(AddHub.class);
    private static final String ADD_HUB_JSP_PATH = ConstantHolder.PATH.getProperty("path.jsp.addHub");
    private static final String ADD_ARTICLE_SERVLET_URL_PATTERN = ConstantHolder.MAPPING
            .getProperty("mapping.servlet.addArticle");
    private static final String ADD_HUB_BUTTON = "addHubButton";
    private static final String HUB_NAME_FIELD = "hubNameField";
    private static final String HUB_DESCRIPTION_FIELD = "hubDescriptionField";
    private static final String HUB_TYPE_FIELD = "hubTypeField";
    private HubService hubService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        hubService = new HubServiceImpl(new HubHibernateDao(HibernateUtil.getSessionFactory()));
        userService = new UserServiceImpl(new UserHibernateDao(HibernateUtil.getSessionFactory()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter(ADD_HUB_BUTTON) == null) {
            request.getRequestDispatcher(ADD_HUB_JSP_PATH).forward(request, response);
            return;
        }

        String hubName = request.getParameter(HUB_NAME_FIELD);
        String hubDescription = request.getParameter(HUB_DESCRIPTION_FIELD);
        HubDto hubDto = new HubDto(hubName, hubDescription, request.getParameter(HUB_TYPE_FIELD));
        Language language = (Language)
                request.getSession().getAttribute(SessionConstant.USER_LANGUAGE_SESSION_ATTRIBUTE);
        Validator validator = Validation.getValidator(language.getLocale());
        Set<ConstraintViolation<HubDto>> validationErrors = validator.validate(hubDto);
        if (!validationErrors.isEmpty()) {
            logger.debug("The hub's validation error messages: {}", validationErrors);
            request.setAttribute("validationErrors", validationErrors);
            request.setAttribute(HUB_NAME_FIELD, hubName);
            request.setAttribute(HUB_DESCRIPTION_FIELD, hubDescription);
            request.getRequestDispatcher(ADD_HUB_JSP_PATH).forward(request, response);
            return;
        }

        try {
            hubService.add(hubDto.createHub(), userService.getByUserName(
                    (String) request.getSession(true).getAttribute(SessionConstant.USER_NAME_SESSION_ATTRIBUTE)));
        } catch (DuplicateHubNameException e) {
            logger.error("The hub's name was duplicated", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } catch (UserNotFoundException e) {
            logger.error("The user not found", e);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        response.sendRedirect(ADD_ARTICLE_SERVLET_URL_PATTERN);
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
