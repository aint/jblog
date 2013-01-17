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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.aint.jblog.model.dao.hibernate.AnonymousMessageHibernateDao;
import com.github.aint.jblog.service.data.PublicMessageService;
import com.github.aint.jblog.service.data.impl.PublicMessageServiceImpl;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.web.constant.ConstantHolder;
import com.github.aint.jblog.web.dto.AnonymousMessageDto;
import com.google.code.kaptcha.Constants;

/**
 * This servlet displays all public messages and adds new public message.
 * 
 * @author Olexandr Tyshkovets
 */
@WebServlet("/public-room")
public class PublicRoom extends HttpServlet {
    private static final long serialVersionUID = 1412954414871561036L;
    private static final String PUBLICROOM_JSP_PATH = ConstantHolder.PATH.getProperty("path.jsp.publicRoom");
    private static final String LEAVE_MESSAGE_BUTTON = "leaveMessageButton";
    private static final String MESSAGE_AUTHOR_FIELD = "messageAuthorField";
    private static final String MESSAGE_BODY_FIELD = "messageBodyField";
    private static final String CAPTCHA_ANSWER_FIELD = "captchaAnswerField";
    private static final Logger logger = LoggerFactory.getLogger(PublicRoom.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter(LEAVE_MESSAGE_BUTTON) != null) {
            String authorName = request.getParameter(MESSAGE_AUTHOR_FIELD);
            String messageBody = request.getParameter(MESSAGE_BODY_FIELD);
            String captchaAnswer = request.getParameter(CAPTCHA_ANSWER_FIELD);

            AnonymousMessageDto messageDto = new AnonymousMessageDto(authorName, messageBody, captchaAnswer,
                    (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY));
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<AnonymousMessageDto>> validationErrors = validator.validate(messageDto);
            if (!validationErrors.isEmpty()) {
                logger.debug("The anonymous message's validation error messages: {}", validationErrors);
                request.setAttribute("validationErrors", validationErrors);
                request.setAttribute(MESSAGE_AUTHOR_FIELD, authorName);
                request.setAttribute(MESSAGE_BODY_FIELD, messageBody);
            } else {
                PublicMessageService messageService = new PublicMessageServiceImpl(
                        new AnonymousMessageHibernateDao(HibernateUtil.getSessionFactory()));
                messageService.add(messageDto.createAnonymousMessage());
            }
        }

        PublicMessageService messageService = new PublicMessageServiceImpl(new AnonymousMessageHibernateDao(
                HibernateUtil.getSessionFactory()));
        request.setAttribute("MESSAGES", messageService.getAll());

        request.getRequestDispatcher(PUBLICROOM_JSP_PATH).forward(request, response);
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
