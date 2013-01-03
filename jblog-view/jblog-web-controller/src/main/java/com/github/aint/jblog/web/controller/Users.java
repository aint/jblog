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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.aint.jblog.model.dao.hibernate.UserHibernateDao;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.data.UserService;
import com.github.aint.jblog.service.data.impl.UserServiceImpl;
import com.github.aint.jblog.service.exception.data.UserNotFoundException;
import com.github.aint.jblog.service.exception.security.AccessException;
import com.github.aint.jblog.service.exception.validator.ValidationException;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.service.util.impl.UserComparatorFactory;
import com.github.aint.jblog.service.validation.Validator;
import com.github.aint.jblog.service.validation.dto.BanUserDto;
import com.github.aint.jblog.service.validation.impl.AnnotationBasedValidator;
import com.github.aint.jblog.web.constant.ConstantHolder;
import com.github.aint.jblog.web.constant.SessionConstant;

/**
 * This servlet displays all users.
 * 
 * @author Olexandr Tyshkovets
 */
@WebServlet("/users")
public class Users extends HttpServlet {
    private static final long serialVersionUID = -2429194892572208132L;
    private static Logger logger = LoggerFactory.getLogger(Users.class);
    private static final String USERS_JSP_PATH = ConstantHolder.PATH.getProperty("path.jsp.users");
    private static final String BAN_UNBAN_BUTTON = "BanUnbanUserButton";
    private static final String BAN_ACTION = "ban";
    private static final String UNBAN_ACTION = "unban";

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService userService = new UserServiceImpl(new UserHibernateDao(HibernateUtil.getSessionFactory()));

        if (request.getParameter(BAN_UNBAN_BUTTON) != null) {
            String action = request.getParameter("action");
            String userName = request.getParameter("userName");
            String banLength = request.getParameter("banLength");
            String banReason = request.getParameter("banReason");

            if (BAN_ACTION.equals(action)) {
                Validator userValidator = new AnnotationBasedValidator();
                Map<String, String> errorMsgMap = new HashMap<String, String>();
                try {
                    errorMsgMap.putAll(userValidator.validate(new BanUserDto(userName, banLength, banReason)));
                } catch (ValidationException e) {
                    logger.error("Some error occured in validation", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
                }

                if (!errorMsgMap.isEmpty()) {
                    logger.debug("The user's validation error messages: {}", errorMsgMap);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMsgMap.toString());
                    return;
                }
            }

            User caller = null;
            User user = null;
            try {
                caller = userService.getByUserName((String) request.getSession(true).getAttribute(
                        SessionConstant.USER_NAME_SESSION_ATTRIBUTE));
                user = userService.getByUserName(userName);
                if (BAN_ACTION.equals(action)) {
                    userService.banUser(caller, user, Integer.parseInt(banLength), banReason);
                } else if (UNBAN_ACTION.equals(action)) {
                    userService.unbanUser(caller, user);
                }
            } catch (UserNotFoundException e) {
                logger.error("Failed to ban/unban: the caller or the user not found", e);
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            } catch (AccessException e) {
                logger.error("The caller" + caller.getUserName() + " doesn't have permission to perform ban/unban", e);
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        userService.deleteNonActivatedUsers();
        List<User> users = userService.getAll();
        Collections.sort(users, Collections.reverseOrder(new UserComparatorFactory().getRatingComparator()));
        request.setAttribute("USERS", users);

        request.getRequestDispatcher(USERS_JSP_PATH).forward(request, response);
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
