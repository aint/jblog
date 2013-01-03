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
import java.util.Arrays;
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
import com.github.aint.jblog.service.data.ArticleService;
import com.github.aint.jblog.service.data.impl.ArticleServiceImpl;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.service.util.impl.ArticleComparatorFactory;
import com.github.aint.jblog.web.constant.ConstantHolder;

/**
 * This servlet displays a list of all articles.
 * 
 * @author Olexandr Tyshkovets
 */
@WebServlet("/articles")
public class Articles extends HttpServlet {
    private static final long serialVersionUID = 143768155658693457L;
    private static Logger logger = LoggerFactory.getLogger(Articles.class);
    private static final String ARTICLES_JSP_PATH = ConstantHolder.PATH.getProperty("path.jsp.articles");
    private static final int DEFAULT_ARTICLES_ON_PAGE = 5;
    private static final List<Integer> ARTICLES_ON_PAGE = Arrays.asList(5, 10, 20);

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer articlesOnPage = 0;
        try {
            articlesOnPage = request.getParameter("articlesOnPage") == null ? DEFAULT_ARTICLES_ON_PAGE :
                    Integer.parseInt(request.getParameter("articlesOnPage"));
            if (!ARTICLES_ON_PAGE.contains(articlesOnPage)) {
                throw new IllegalArgumentException("param not entry in " + ARTICLES_ON_PAGE);
            }
        } catch (IllegalArgumentException e) {
            logger.warn("The articlesOnPage parameter is uncorrect {} {}", e.getClass(), e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int page = 0;
        List<Article> articles = null;
        ArticleService articleService = new ArticleServiceImpl(
                new ArticleHibernateDao(HibernateUtil.getSessionFactory()));
        try {
            page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
            articles = articleService.getAllOnPage(page, articlesOnPage, false);
        } catch (NumberFormatException e) {
            logger.warn("The page parameter is uncorrect {} {}", e.getClass(), e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (IllegalArgumentException e) {
            logger.warn("The page parameter is invalid", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (articles.size() == 0 && page > 1) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        long articleCount = articleService.getCount();
        long pageCount = articleCount % articlesOnPage == 0 ? articleCount / articlesOnPage :
                articleCount / articlesOnPage + 1;
        List<Integer> pages = new ArrayList<Integer>();
        for (int i = 1; i <= pageCount; i++) {
            pages.add(i);
        }
        request.setAttribute("pages", pages);

        Collections.sort(articles, Collections.reverseOrder(new ArticleComparatorFactory().getDateComparator()));
        request.setAttribute("articles", articles);

        request.getRequestDispatcher(ARTICLES_JSP_PATH).forward(request, response);
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
