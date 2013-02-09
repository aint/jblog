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
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.aint.jblog.model.dao.hibernate.ArticleHibernateDao;
import com.github.aint.jblog.model.dao.hibernate.NewsHibernateDao;
import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.model.entity.News;
import com.github.aint.jblog.service.data.ArticleService;
import com.github.aint.jblog.service.data.NewsService;
import com.github.aint.jblog.service.data.impl.ArticleServiceImpl;
import com.github.aint.jblog.service.data.impl.NewsServiceImpl;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.service.util.impl.ArticleComparatorFactory;
import com.github.aint.jblog.web.constant.ConstantHolder;

/**
 * This servlet displays the most popular articles and the latest news.
 * 
 * @author Olexandr Tyshkovets
 */
@WebServlet("/index")
public class Index extends HttpServlet {
    private static final long serialVersionUID = -5253243891912922807L;
    private static final String INDEX_JSP_PATH = ConstantHolder.PATH.getProperty("path.jsp.index");
    private static final long ONE_DAY = 1000 * 60 * 60 * 24;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArticleService articleService = new ArticleServiceImpl(new ArticleHibernateDao(
                HibernateUtil.getSessionFactory()));
        List<Article> popularArticles = articleService.getMostPopularArticles(3, 5);
        Collections.sort(popularArticles, Collections.reverseOrder(new ArticleComparatorFactory().getDateComparator()));
        request.setAttribute("articles", popularArticles);

        NewsService newsService = new NewsServiceImpl(new NewsHibernateDao(HibernateUtil.getSessionFactory()));
        Set<News> news = new LinkedHashSet<News>(
                newsService.getNewsCreatedSince(new Date(System.currentTimeMillis() - ONE_DAY * 31)));
        news.addAll(newsService.getAllPinnedNews());
        request.setAttribute("news", news);

        request.getRequestDispatcher(INDEX_JSP_PATH).forward(request, response);
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
