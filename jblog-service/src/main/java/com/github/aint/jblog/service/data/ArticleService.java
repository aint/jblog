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

import java.util.List;

import com.github.aint.jblog.model.dao.VoiceForArticleDao;
import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.model.entity.Hub;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.model.entity.VoiceValue;
import com.github.aint.jblog.service.data.impl.ArticleServiceImpl;

/**
 * This interface represents methods to operate with a {@link Article}.
 * 
 * @author Olexandr Tyshkovets
 * @see GenericEntityService
 * @see ArticleServiceImpl
 */
public interface ArticleService extends GenericEntityService<Article> {
    /**
     * Adds a new {@link Article}.
     * 
     * @param article
     *            an {@link Article} which will be added
     * @param author
     *            the author of the given {@code article}
     * @param hub
     *            the container for the given {@code article}
     * @return an added {@link Article}
     * 
     * @see Hub
     * @see User
     */
    Article add(Article article, User author, Hub hub);

    /**
     * Votes for an {@link Article}.
     * 
     * @param article
     *            an {@link Article} for which will vote
     * @param user
     *            a {@link User} who has voted for this {@code article}
     * @param voiceValue
     *            the positive or negative voice
     * @param voiceDao
     *            an implementation of a {@link VoiceForArticleDao} which create the new voice
     */
    void voteForArticle(Article article, User user, VoiceValue voiceValue, VoiceForArticleDao voiceDao);

    /**
     * Returns the most popular articles.
     * 
     * @param minRating
     *            the minimum rating of articles, inclusive
     * @param maxSize
     *            the maximum size of the returned list
     * @return a list of the most popular articles
     */
    List<Article> getMostPopularArticles(int minRating, int maxSize);

    /**
     * Increases amount of views of the given {@code article}.
     * 
     * @param article
     *            the article whose amount of views will be increased
     */
    void increaseAmountOfViews(Article article);

}
