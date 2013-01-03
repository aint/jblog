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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.aint.jblog.model.dao.hibernate.ArticleHibernateDao;
import com.github.aint.jblog.model.dao.hibernate.CommentHibernateDao;
import com.github.aint.jblog.model.dao.hibernate.UserHibernateDao;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.data.ArticleService;
import com.github.aint.jblog.service.data.CommentService;
import com.github.aint.jblog.service.data.UserService;
import com.github.aint.jblog.service.data.impl.ArticleServiceImpl;
import com.github.aint.jblog.service.data.impl.CommentServiceImpl;
import com.github.aint.jblog.service.data.impl.UserServiceImpl;
import com.github.aint.jblog.service.exception.data.EntityNotFoundException;
import com.github.aint.jblog.service.exception.data.UserNotFoundException;
import com.github.aint.jblog.service.exception.validator.ValidationException;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.service.validation.Validator;
import com.github.aint.jblog.service.validation.dto.CommentDto;
import com.github.aint.jblog.service.validation.impl.AnnotationBasedValidator;
import com.github.aint.jblog.web.constant.ConstantHolder;
import com.github.aint.jblog.web.constant.SessionConstant;

/**
 * This servlet adds a comment.
 * 
 * @author Olexandr Tyshkovets
 */
@WebServlet("/add-comment")
public class AddComment extends HttpServlet {
    private static final long serialVersionUID = 4813185927824055034L;
    private static final Logger logger = LoggerFactory.getLogger(AddComment.class);
    private static final String ARTICLES_URL_PATTERN = ConstantHolder.MAPPING.getProperty("mapping.servlet.articles");
    private static final String DISPLAY_ARTICLE_URL_PATTERN = ConstantHolder.MAPPING
            .getProperty("mapping.servlet.displayArticle");
    private static final String LEAVE_COMMENT_BUTTON = "leaveCommentButton";
    private static final String COMMENT_BODY_FIELD = "commentBodyField";
    private CommentService commentService;
    private ArticleService articleService;
    private UserService userService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        commentService = new CommentServiceImpl(new CommentHibernateDao(HibernateUtil.getSessionFactory()));
        articleService = new ArticleServiceImpl(new ArticleHibernateDao(HibernateUtil.getSessionFactory()));
        userService = new UserServiceImpl(new UserHibernateDao(HibernateUtil.getSessionFactory()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter(LEAVE_COMMENT_BUTTON) == null) {
            response.sendRedirect(ARTICLES_URL_PATTERN);
            return;
        }

        User author = null;
        try {
            author = userService.getByUserName((String) request.getSession(true).getAttribute(
                    SessionConstant.USER_NAME_SESSION_ATTRIBUTE));
        } catch (UserNotFoundException e) {
            logger.error("The user was not found", e);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Long articleId = null;
        try {
            articleId = Long.valueOf(request.getParameter("articleId"));
        } catch (NumberFormatException e) {
            logger.warn("The article's id parameter is uncorrect {} {}", e.getClass(), e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String commentBody = request.getParameter(COMMENT_BODY_FIELD);
        CommentDto commentDto = new CommentDto(commentBody);
        Validator commentValidator = new AnnotationBasedValidator();
        Map<String, String> errorMsgMap = new HashMap<String, String>();
        try {
            errorMsgMap.putAll(commentValidator.validate(commentDto));
        } catch (ValidationException e) {
            logger.error("Some error occured in validation", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        if (!errorMsgMap.isEmpty()) {
            logger.debug("The comment's validation error messages: {}", errorMsgMap);
            request.getSession().setAttribute("errorMsgMap", errorMsgMap);
            request.getSession().setAttribute(COMMENT_BODY_FIELD, commentBody);
            response.sendRedirect(DISPLAY_ARTICLE_URL_PATTERN + "/" + articleId);
            return;
        }

        try {
            commentService.addComment(commentDto.createComment(), author, articleService.get(articleId));
        } catch (EntityNotFoundException e) {
            logger.info("The article not found", e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.sendRedirect(DISPLAY_ARTICLE_URL_PATTERN + "/" + articleId);
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
