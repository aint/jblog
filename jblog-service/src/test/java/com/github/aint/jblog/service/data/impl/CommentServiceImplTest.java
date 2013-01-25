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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.aint.jblog.model.dao.CommentDao;
import com.github.aint.jblog.model.dao.hibernate.VoiceForCommentHibernateDao;
import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.model.entity.Comment;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.model.entity.VoiceForComment;
import com.github.aint.jblog.model.entity.VoiceValue;
import com.github.aint.jblog.service.data.CommentService;
import com.github.aint.jblog.service.exception.data.CommentNotFoundException;
import com.github.aint.jblog.service.exception.data.EntityNotFoundException;

/**
 * @author Olexandr Tyshkovets
 */
public class CommentServiceImplTest {
    private static final Long COMMENT_ID = 1L;
    private static final User COMMENT_AUTHOR = new User("userName", "user@gmail.com", "password");
    private static final int COMMENT_RATING = 0;
    private static final Article ARTICLE = new Article("title", "preview", "body", "keywords", COMMENT_AUTHOR, null);
    private CommentService commentService;
    @Mock
    private CommentDao commentDao;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
        commentService = new CommentServiceImpl(commentDao);
    }

    /* ----- own methods ----- */

    @Test
    public void addComment() {
        final Comment comment = getComment();
        commentService.addComment(comment, COMMENT_AUTHOR, ARTICLE);

        assertNotNull(comment.getAuthor(), "comment's author is null");
        assertNotNull(comment.getArticle(), "comment's article is null");
        assertEquals(comment.getRating(), 0, "comment's rating isn't 0");
        assertNotNull(comment.getCreationDate(), "comment's creationDate is null");

        verify(commentDao).save(comment);
    }

    @Test
    public void voteForComment() {
        final Comment comment = getComment();
        final VoiceValue voice = VoiceValue.NEGATIVE;
        VoiceForCommentHibernateDao voiceDao = mock(VoiceForCommentHibernateDao.class);
        commentService.voteForComment(comment, COMMENT_AUTHOR, voice, voiceDao);

        assertEquals(comment.getRating(), COMMENT_RATING - 1);

        verify(voiceDao).save(new VoiceForComment(voice, comment, COMMENT_AUTHOR));
    }

    /* ----- common methods ----- */

    @Test
    public void get() throws EntityNotFoundException {
        final Comment expected = getComment();
        when(commentDao.get(COMMENT_ID)).thenReturn(expected);

        assertEquals(commentService.get(COMMENT_ID), expected);

        verify(commentDao).get(COMMENT_ID);
    }

    @Test(expectedExceptions = CommentNotFoundException.class)
    public void getNotFound() throws EntityNotFoundException {
        when(commentDao.get(COMMENT_ID)).thenReturn(null);

        commentService.get(COMMENT_ID);
    }

    @Test
    public void getAll() {
        final List<Comment> expected = new ArrayList<Comment>(Arrays.asList(getComment(), getComment(), getComment()));
        when(commentDao.getAll()).thenReturn(expected);

        assertEquals(commentService.getAll(), expected);

        verify(commentDao).getAll();
    }

    @Test
    public void getAllOnPage() {
        final List<Comment> expected = new ArrayList<Comment>(Arrays.asList(getComment(), getComment(), getComment()));
        when(commentDao.getAllOnPage(1, 5, true)).thenReturn(expected);

        assertEquals(commentService.getAllOnPage(1, 5, true), expected);

        verify(commentDao).getAllOnPage(1, 5, true);
    }

    @Test
    public void getCount() {
        final long expected = 13L;
        when(commentDao.getCount()).thenReturn(expected);

        assertEquals(commentService.getCount(), expected);

        verify(commentDao).getCount();
    }

    @Test
    public void isExist() {
        when(commentDao.get(COMMENT_ID)).thenReturn(null);

        assertFalse(commentService.isExist(COMMENT_ID));
    }

    private Comment getComment() {
        Comment comment = new Comment("body", ARTICLE, COMMENT_AUTHOR);
        comment.setId(COMMENT_ID);
        comment.setCreationDate(new Date());
        comment.setRating(COMMENT_RATING);
        return comment;
    }
}
