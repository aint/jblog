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
package com.github.aint.jblog.web.view.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.aint.jblog.model.dao.hibernate.UserHibernateDao;
import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.model.entity.Role;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.data.UserService;
import com.github.aint.jblog.service.data.impl.UserServiceImpl;
import com.github.aint.jblog.service.exception.data.UserNotFoundException;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.web.constant.SessionConstant;

/**
 * This tag checks whether the current user have permission to edit the given article.
 * 
 * @author Olexandr Tyshkovets
 */
public class IfCanEditArticle extends ConditionalTagSupport {
    private static final long serialVersionUID = 8031355688488905085L;
    private static Logger logger = LoggerFactory.getLogger(IfCanEditArticle.class);
    private static final long ONE_HOUR = 1000 * 60 * 60 * 24;
    private Article article;

    /**
     * @param article
     *            the article to set
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean condition() throws JspTagException {
        if (article == null) {
            logger.error("The given article is null");
            throw new JspTagException("The given article is null");
        }

        String userName = (String) pageContext.getSession().getAttribute(SessionConstant.USER_NAME_SESSION_ATTRIBUTE);
        UserService userService = new UserServiceImpl(new UserHibernateDao(HibernateUtil.getSessionFactory()));
        User user = null;
        try {
            user = userService.getByUserName(userName);
        } catch (UserNotFoundException e) {
            logger.error("The user not found", e);
            throw new JspTagException("The user not found", e);
        }

        if (user.getUserName().equals(article.getAuthor().getUserName()) &&
                System.currentTimeMillis() - article.getCreationDate().getTime() < ONE_HOUR ||
                user.getRole() == Role.MODERATOR || user.getRole() == Role.ADMIN) {
            return true;
        }

        return false;
    }

}
