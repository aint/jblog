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
package com.github.aint.jblog.service.data.impl;

import java.util.Date;
import java.util.List;

import com.github.aint.jblog.model.dao.CommentDao;
import com.github.aint.jblog.model.dao.VoiceForCommentDao;
import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.model.entity.Comment;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.model.entity.VoiceForComment;
import com.github.aint.jblog.model.entity.VoiceValue;
import com.github.aint.jblog.service.data.CommentService;
import com.github.aint.jblog.service.exception.data.CommentNotFoundException;

/**
 * This class implementing the {@code CommentService} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see CommentService
 */
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;

    /**
     * Constructs a {@code CommentServiceImpl} with the given {@code commentDao}.
     * 
     * @param commentDao
     *            the object, which necessary for operations with a data source
     * @see CommentDao
     */
    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comment addComment(Comment comment, User author, Article article) {
        comment.setAuthor(author);
        comment.setArticle(article);
        comment.setCreationDate(new Date());
        comment.setRating(0);
        commentDao.save(comment);

        return comment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void voteForComment(Comment comment, User user, VoiceValue voiceValue, VoiceForCommentDao voiceDao) {
        voiceDao.save(new VoiceForComment(voiceValue, comment, user));
        comment.setRating(VoiceValue.NEGATIVE.equals(voiceValue) ? comment.getRating() - 1 : comment.getRating() + 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comment get(Long id) throws CommentNotFoundException {
        Comment comment = commentDao.get(id);
        if (comment == null) {
            throw new CommentNotFoundException("The comment with the given id: " + id + " was not found");
        }
        return comment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Comment> getAll() {
        return commentDao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Comment> getAllOnPage(int pageNumber, int pageSize, boolean head) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("pageNumber cant'be <= 0");
        }

        return commentDao.getAllOnPage(pageNumber, pageSize, head);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getCount() {
        return commentDao.getCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExist(Long id) {
        return commentDao.get(id) != null;
    }

}
