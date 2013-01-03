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

import com.github.aint.jblog.model.dao.ArticleDao;
import com.github.aint.jblog.model.dao.VoiceForArticleDao;
import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.model.entity.VoiceForArticle;
import com.github.aint.jblog.model.entity.VoiceValue;
import com.github.aint.jblog.service.data.ArticleService;
import com.github.aint.jblog.service.exception.data.ArticleNotFoundException;
import com.github.aint.jblog.service.exception.data.EntityNotFoundException;

/**
 * @author Olexandr Tyshkovets
 */
public class ArticleServiceImplTest {
    private static final Long ARTICLE_ID = 1L;
    private static final int ARTICLE_RATING = 0;
    private static final User ARTICLE_AUTHOR = new User("userName", "user@gmail.com", "password");
    private ArticleService articleService;
    @Mock
    private ArticleDao articleDao;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
        articleService = new ArticleServiceImpl(articleDao);
    }

    /* ----- own methods ----- */

    @Test
    public void add() {
        final Article article = getArticle();
        articleService.add(article, ARTICLE_AUTHOR);

        assertEquals(article.getRating(), 0, "article's rating isn't 0");
        assertNotNull(article.getCreationDate(), "article's creationDate is null");
        assertNotNull(article.getAuthor(), "article's author is null");

        verify(articleDao).save(article);
    }

    @Test
    public void getMostPopularArticles() {
        final List<Article> expected = new ArrayList<Article>(Arrays.asList(
                getArticle(),
                getArticle(),
                getArticle()
                ));
        when(articleDao.getMostPopularArticles(0, 5)).thenReturn(expected);

        assertEquals(articleService.getMostPopularArticles(0, 5), expected);

        verify(articleDao).getMostPopularArticles(0, 5);
    }

    @Test
    public void voteForArticle() {
        final Article article = getArticle();
        final VoiceValue voice = VoiceValue.NEGATIVE;
        VoiceForArticleDao voiceDao = mock(VoiceForArticleDao.class);
        articleService.voteForArticle(article, ARTICLE_AUTHOR, voice, voiceDao);

        assertEquals(article.getRating(), ARTICLE_RATING - 1);

        verify(voiceDao).save(new VoiceForArticle(voice, article, ARTICLE_AUTHOR));
    }

    /* ===== common methods ===== */

    @Test
    public void get() throws EntityNotFoundException {
        final Article expected = getArticle();
        when(articleDao.get(ARTICLE_ID)).thenReturn(expected);

        assertEquals(articleService.get(ARTICLE_ID), expected);

        verify(articleDao).get(ARTICLE_ID);
    }

    @Test(expectedExceptions = ArticleNotFoundException.class)
    public void getNotFound() throws EntityNotFoundException {
        when(articleDao.get(ARTICLE_ID)).thenReturn(null);

        articleService.get(ARTICLE_ID);
    }

    @Test
    public void getAll() {
        final List<Article> expected = new ArrayList<Article>(Arrays.asList(getArticle(), getArticle(), getArticle()));
        when(articleDao.getAll()).thenReturn(expected);

        assertEquals(articleService.getAll(), expected);

        verify(articleDao).getAll();
    }

    @Test
    public void getAllOnPage() {
        final List<Article> expected = new ArrayList<Article>(Arrays.asList(getArticle(), getArticle()));
        when(articleDao.getAllOnPage(1, 5, true)).thenReturn(expected);

        assertEquals(articleService.getAllOnPage(1, 5, true), expected);

        verify(articleDao).getAllOnPage(1, 5, true);
    }

    @Test
    public void getCount() {
        final long expected = 13L;
        when(articleDao.getCount()).thenReturn(expected);

        assertEquals(articleService.getCount(), expected);

        verify(articleDao).getCount();
    }

    @Test
    public void isExist() {
        when(articleDao.get(ARTICLE_ID)).thenReturn(null);

        assertFalse(articleService.isExist(ARTICLE_ID));
    }

    private Article getArticle() {
        Article article = new Article("title", "preview", "body", "keywords");
        article.setId(ARTICLE_ID);
        article.setCreationDate(new Date());
        article.setRating(ARTICLE_RATING);
        article.setAuthor(ARTICLE_AUTHOR);
        return article;
    }

}
