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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.aint.jblog.model.dao.hibernate.ArticleHibernateDao;
import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.model.entity.Comment;
import com.github.aint.jblog.service.data.ArticleService;
import com.github.aint.jblog.service.data.impl.ArticleServiceImpl;
import com.github.aint.jblog.service.exception.data.EntityNotFoundException;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.service.util.impl.CommentComparatorFactory;
import com.github.aint.jblog.web.constant.ConstantHolder;

/**
 * This servlet displays an article.
 * 
 * @author Olexandr Tyshkovets
 */
@WebServlet("/display-article")
public class DisplayArticle extends HttpServlet {
    private static final long serialVersionUID = 229477669983586549L;
    private static Logger logger = LoggerFactory.getLogger(DisplayArticle.class);
    private static final String DISPLAY_ARTICLE_JSP_PATH = ConstantHolder.PATH.getProperty("path.jsp.displayArticle");
    private static final String COMMENT_BODY_FIELD = "commentBodyField";

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArticleService articleService = new ArticleServiceImpl(
                new ArticleHibernateDao(HibernateUtil.getSessionFactory()));
        Article article = null;
        try {
            article = articleService.get(Long.valueOf(request.getParameter("articleId")));
        } catch (NumberFormatException e) {
            logger.warn("The article's id parameter is uncorrect {} {}" + e.getClass() + " " + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (EntityNotFoundException e) {
            logger.info("The article not found", e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        articleService.increaseAmountOfViews(article);

        Object errors = request.getSession(true).getAttribute("errorMsgMap");
        if (errors != null) {
            request.setAttribute("errorMsgMap", errors);
            request.setAttribute(COMMENT_BODY_FIELD, request.getSession().getAttribute(COMMENT_BODY_FIELD));
            request.getSession().removeAttribute("errorMsgMap");
            request.getSession().removeAttribute(COMMENT_BODY_FIELD);
        }
        request.setAttribute("article", article);
        List<Comment> comments = new ArrayList<Comment>(article.getComments());
        Collections.sort(comments, new CommentComparatorFactory().getDateComparator());
        request.setAttribute("COMMENTS", comments);

        request.getRequestDispatcher(DISPLAY_ARTICLE_JSP_PATH).forward(request, response);
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
