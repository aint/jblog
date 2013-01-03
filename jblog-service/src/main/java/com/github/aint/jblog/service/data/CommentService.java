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
package com.github.aint.jblog.service.data;

import com.github.aint.jblog.model.dao.VoiceForCommentDao;
import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.model.entity.Comment;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.model.entity.VoiceValue;
import com.github.aint.jblog.service.data.impl.CommentServiceImpl;

/**
 * This interface represents methods to operate with a {@link Comment}.
 * 
 * @author Olexandr Tyshkovets
 * @see GenericEntityService
 * @see CommentServiceImpl
 */
public interface CommentService extends GenericEntityService<Comment> {
    /**
     * Adds a new {@link Comment}.
     * 
     * @param comment
     *            a {@link Comment} which will be added
     * @param author
     *            a {@link User} who is an author of the {@code comment}
     * @param article
     *            a {@link Article}, which will get the new {@link Comment}
     * @return an added {@link Comment}
     */
    Comment addComment(Comment comment, User author, Article article);

    /**
     * Votes for a {@link Comment}.
     * 
     * @param comment
     *            a {@link Comment} for which will vote
     * @param user
     *            a {@link User} who has voted for this {@code comment}
     * @param voiceValue
     *            the positive or negative voice
     * @param voiceDao
     *            an implementation of a {@link VoiceForCommentDao} which create the new voice
     * @see VoiceValue
     */
    void voteForComment(Comment comment, User user, VoiceValue voiceValue, VoiceForCommentDao voiceDao);

}
