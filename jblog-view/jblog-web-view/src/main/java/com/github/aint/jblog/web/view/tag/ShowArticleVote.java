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
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.model.entity.VoiceForArticle;
import com.github.aint.jblog.service.data.impl.UserServiceImpl;
import com.github.aint.jblog.service.exception.data.UserNotFoundException;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.web.constant.SessionConstant;

/**
 * This tag checks whether the current user voted for the given article.
 * 
 * @author Olexandr Tyshkovets
 */
public class ShowArticleVote extends ConditionalTagSupport {
    private static final long serialVersionUID = -8757573878706906207L;
    private static Logger logger = LoggerFactory.getLogger(ShowArticleVote.class);
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
            logger.error("The article is null");
            throw new JspTagException("The article is null");
        }

        User user = null;
        try {
            user = new UserServiceImpl(new UserHibernateDao(HibernateUtil.getSessionFactory())).getByUserName(
                    (String) pageContext.getSession().getAttribute(SessionConstant.USER_NAME_SESSION_ATTRIBUTE));
        } catch (UserNotFoundException e) {
            logger.error("The user not found", e);
            throw new JspTagException("The user is null");
        }

        for (VoiceForArticle voice : article.getVoices()) {
            if (voice.getAuthor().equals(user)) {
                return false;
            }
        }

        return true;
    }

}
