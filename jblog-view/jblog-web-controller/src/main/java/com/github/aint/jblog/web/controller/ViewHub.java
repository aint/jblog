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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.aint.jblog.model.dao.hibernate.HubHibernateDao;
import com.github.aint.jblog.model.entity.Hub;
import com.github.aint.jblog.service.data.impl.HubServiceImpl;
import com.github.aint.jblog.service.exception.data.EntityNotFoundException;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.web.constant.ConstantHolder;

/**
 * This servlet displays the hub's articles.
 * 
 * @author Olexandr Tyshkovets
 */
@WebServlet("/hub")
public class ViewHub extends HttpServlet {
    private static final long serialVersionUID = 1852894164024160460L;
    private static Logger logger = LoggerFactory.getLogger(ViewHub.class);
    private static final String VIEW_HUB_JSP_PATH = ConstantHolder.PATH.getProperty("path.jsp.viewHub");

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Hub hub = null;
        try {
            hub = new HubServiceImpl(new HubHibernateDao(HibernateUtil.getSessionFactory()))
                    .get(Long.valueOf(request.getParameter("hubId")));
        } catch (NumberFormatException e) {
            logger.warn("The hub's id parameter is incorrect. {} {}", e.getClass(), e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (EntityNotFoundException e) {
            logger.info("The hub not found. {} {}", e.getClass(), e.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setAttribute("HUB", hub);
        request.setAttribute("ARTICLES", hub.getArticles());

        request.getRequestDispatcher(VIEW_HUB_JSP_PATH).forward(request, response);
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
