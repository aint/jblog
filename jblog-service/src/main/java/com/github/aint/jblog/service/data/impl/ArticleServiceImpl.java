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

import com.github.aint.jblog.model.dao.ArticleDao;
import com.github.aint.jblog.model.dao.VoiceForArticleDao;
import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.model.entity.Hub;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.model.entity.VoiceForArticle;
import com.github.aint.jblog.model.entity.VoiceValue;
import com.github.aint.jblog.service.data.ArticleService;
import com.github.aint.jblog.service.exception.data.ArticleNotFoundException;

/**
 * This class implementing the {@code ArticleService} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see ArticleService
 */
public class ArticleServiceImpl implements ArticleService {
    private final ArticleDao articleDao;

    /**
     * Constructs a {@code ArticleServiceImpl} with the given {@code articleDao}.
     * 
     * @param articleDao
     *            the object, which necessary for operations with a data source
     * @see ArticleDao
     */
    public ArticleServiceImpl(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Article add(Article article, User author, Hub hub) {
        article.setRating(0);
        article.setViews(0);
        article.setCreationDate(new Date());
        article.setAuthor(author);
        article.setHub(hub);
        articleDao.save(article);

        return article;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void voteForArticle(Article article, User user, VoiceValue voiceValue, VoiceForArticleDao voiceDao) {
        voiceDao.save(new VoiceForArticle(voiceValue, article, user));
        article.setRating(VoiceValue.NEGATIVE.equals(voiceValue) ? article.getRating() - 1 : article.getRating() + 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Article> getMostPopularArticles(int minRating, int maxSize) {
        return articleDao.getMostPopularArticles(minRating, maxSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseAmountOfViews(Article article) {
        article.setViews(article.getViews() + 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Article> getArticlesOfUser(User user) {
        return articleDao.getArticlesOfUser(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Article get(Long id) throws ArticleNotFoundException {
        Article article = articleDao.get(id);
        if (article == null) {
            throw new ArticleNotFoundException("The article with the given id: " + id + " was not found");
        }
        return article;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Article> getAll() {
        return articleDao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Article> getAllOnPage(int pageNumber, int pageSize, boolean head) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("pageNumber cant'be <= 0");
        }

        return articleDao.getAllOnPage(pageNumber, pageSize, head);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getCount() {
        return articleDao.getCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExist(Long id) {
        return articleDao.get(id) != null;
    }

}
