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

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.github.aint.jblog.model.dao.hibernate.ArticleHibernateDao;
import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.data.impl.ArticleServiceImpl;
import com.github.aint.jblog.service.util.HibernateUtil;

/**
 * This tag prints a hypertext link to the latest article. </p> The link format:
 * {@code <a href='display-article/article.id'>article.title</a>}
 * 
 * @author Olexandr Tyshkovets
 */
public class LinkToLatestArticle extends TagSupport {
    private static final long serialVersionUID = -2423099276373902775L;
    private User user;

    /**
     * {@inheritDoc}
     */
    @Override
    public int doStartTag() throws JspException {
        Article article = new ArticleServiceImpl(new ArticleHibernateDao(HibernateUtil.getSessionFactory()))
                .getLatestArticleOfUser(user);
        try {
            pageContext.getOut()
                    .print("<a href='display-article/" + article.getId() + "'>" + article.getTitle() + "</a>");
            return SKIP_BODY;
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

}
