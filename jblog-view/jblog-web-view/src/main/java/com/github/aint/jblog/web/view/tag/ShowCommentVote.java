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
import com.github.aint.jblog.model.entity.Comment;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.model.entity.VoiceForComment;
import com.github.aint.jblog.service.data.impl.UserServiceImpl;
import com.github.aint.jblog.service.exception.data.UserNotFoundException;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.web.constant.SessionConstant;

/**
 * This tag checks whether the current user voted for the given comment.
 * 
 * @author Olexandr Tyshkovets
 */
public class ShowCommentVote extends ConditionalTagSupport {
    private static final long serialVersionUID = -4468064647604393978L;
    private static Logger logger = LoggerFactory.getLogger(ShowCommentVote.class);
    private Comment comment;

    /**
     * @param comment
     *            the comment to set
     */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean condition() throws JspTagException {
        if (comment == null) {
            logger.error("The comment is null");
            throw new JspTagException("The comment is null");
        }

        User user = null;
        try {
            user = new UserServiceImpl(new UserHibernateDao(HibernateUtil.getSessionFactory())).getByUserName(
                    (String) pageContext.getSession().getAttribute(SessionConstant.USER_NAME_SESSION_ATTRIBUTE));
        } catch (UserNotFoundException e) {
            logger.error("The user not found", e);
            throw new JspTagException("The user not found");
        }

        for (VoiceForComment voice : comment.getVoices()) {
            if (voice.getAuthor().equals(user)) {
                return false;
            }
        }

        return true;
    }

}
