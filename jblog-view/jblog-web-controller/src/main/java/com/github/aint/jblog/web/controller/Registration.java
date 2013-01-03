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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.aint.jblog.model.dao.hibernate.UserHibernateDao;
import com.github.aint.jblog.model.entity.Language;
import com.github.aint.jblog.service.data.impl.UserServiceImpl;
import com.github.aint.jblog.service.exception.data.DuplicateEmailException;
import com.github.aint.jblog.service.exception.data.DuplicateUserNameException;
import com.github.aint.jblog.service.exception.validator.ValidationException;
import com.github.aint.jblog.service.mail.MailConfigurator;
import com.github.aint.jblog.service.mail.impl.JavaxMailSender;
import com.github.aint.jblog.service.mail.impl.MailServiceImpl;
import com.github.aint.jblog.service.mail.impl.PropertiesMailConfigurator;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.service.validation.Validator;
import com.github.aint.jblog.service.validation.dto.RegisterUserDto;
import com.github.aint.jblog.service.validation.impl.AnnotationBasedValidator;
import com.github.aint.jblog.web.constant.ConstantHolder;
import com.github.aint.jblog.web.constant.SessionConstant;

/**
 * This servlet sign up a user.
 * 
 * @author Olexandr Tyshkovets
 */
@WebServlet("/registration")
public class Registration extends HttpServlet {
    private static final long serialVersionUID = -6168382087686478899L;
    private static Logger logger = LoggerFactory.getLogger(Registration.class);
    private static final String REGISTRATION_JSP_PATH = ConstantHolder.PATH.getProperty("path.jsp.registration");
    private static final String SUCCESSFUL_REGISTRATION_JSP_PATH = ConstantHolder.PATH
            .getProperty("path.jsp.successfulRegistration");
    private static final String REGISTRATION_BUTTON = "registrationButton";
    private static final String FIRST_NAME_FIELD = "firstNameField";
    private static final String LAST_NAME_FIELD = "lastNameField";
    private static final String USER_NAME_FIELD = "userNameField";
    private static final String USER_EMAIL_FIELD = "emailField";
    private static final String USER_PASSWORD_FIELD = "passwordField";
    private static final String USER_REPASSWORD_FIELD = "rePasswordField";
    private static final String YEAR_FIELD = "yearField";
    private static final String MONTH_FIELD = "monthField";
    private static final String DAY_FIELD = "dayField";
    private static final String LICENSE_FIELD = "acceptLicenseField";
    private static final String MAIL_CONFIG_PATH = "/com/github/aint/jblog/service/mail/mail-config.properties";
    private static final String VELOCITY_CONFIG_PATH = "/com/github/aint/jblog/service/mail/velocity-config.properties";

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter(REGISTRATION_BUTTON) == null) {
            request.getRequestDispatcher(REGISTRATION_JSP_PATH).forward(request, response);
            return;
        }

        String firstName = request.getParameter(FIRST_NAME_FIELD);
        String lastName = request.getParameter(LAST_NAME_FIELD);
        String userName = request.getParameter(USER_NAME_FIELD);
        String userEmail = request.getParameter(USER_EMAIL_FIELD);
        String userPassword = request.getParameter(USER_PASSWORD_FIELD);
        String userRePassword = request.getParameter(USER_REPASSWORD_FIELD);
        String year = request.getParameter(YEAR_FIELD);
        String month = request.getParameter(MONTH_FIELD);
        String day = request.getParameter(DAY_FIELD);

        RegisterUserDto userDto = new RegisterUserDto(userName, firstName, lastName, userEmail, userPassword,
                userRePassword, day, month, year, (Language) request.getSession(true).getAttribute(
                        SessionConstant.USER_LANGUAGE_SESSION_ATTRIBUTE));

        Validator userValidator = new AnnotationBasedValidator();
        Map<String, String> errorMsgMap = new HashMap<String, String>();
        try {
            errorMsgMap.putAll(userValidator.validate(userDto));
        } catch (ValidationException e) {
            logger.error("Some error occured in validation", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        if (request.getParameter(LICENSE_FIELD) == null) {
            errorMsgMap.put("license", "license agreement not accepts");
        }

        if (!errorMsgMap.isEmpty()) {
            logger.debug("The register user's validation error messages: {}", errorMsgMap);
            request.setAttribute("errorMsgMap", errorMsgMap);
            request.setAttribute(FIRST_NAME_FIELD, firstName);
            request.setAttribute(LAST_NAME_FIELD, lastName);
            request.setAttribute(USER_NAME_FIELD, userName);
            request.setAttribute(USER_EMAIL_FIELD, userEmail);
            request.getRequestDispatcher(REGISTRATION_JSP_PATH).forward(request, response);
            return;
        }

        MailConfigurator mailSenderConfig = null;
        try {
            mailSenderConfig = new PropertiesMailConfigurator(
                    getClass().getResourceAsStream(MAIL_CONFIG_PATH));
        } catch (IOException e) {
            logger.error("Error occurred in email configuration", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream(VELOCITY_CONFIG_PATH));
        } catch (IOException e) {
            logger.error("Failed to initialise velocity ", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        VelocityEngine velocityEngine = new VelocityEngine(prop);
        String applicationUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
        try {
            new UserServiceImpl(
                    new UserHibernateDao(HibernateUtil.getSessionFactory())).registerUser(userDto.createUser(),
                    new MailServiceImpl(new JavaxMailSender(mailSenderConfig), velocityEngine, applicationUrl));
        } catch (DuplicateUserNameException e) {
            logger.info("The userName was duplicated", e);
            errorMsgMap.put("userName", "this userName is busy");
        } catch (DuplicateEmailException e) {
            logger.info("The user's email was duplicated", e);
            errorMsgMap.put("email", "this email is busy");
        }
        // TODO eliminate duplicated code
        if (!errorMsgMap.isEmpty()) {
            logger.debug("The register user's validation error messages: {}", errorMsgMap);
            request.setAttribute("errorMsgMap", errorMsgMap);
            request.setAttribute(FIRST_NAME_FIELD, firstName);
            request.setAttribute(LAST_NAME_FIELD, lastName);
            request.setAttribute(USER_NAME_FIELD, userName);
            request.setAttribute(USER_EMAIL_FIELD, userEmail);
            request.getRequestDispatcher(REGISTRATION_JSP_PATH).forward(request, response);
            return;
        }

        request.getRequestDispatcher(SUCCESSFUL_REGISTRATION_JSP_PATH).forward(request, response);
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
