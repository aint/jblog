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
package com.github.aint.jblog.model.util;

import java.util.Date;

import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.model.entity.Comment;
import com.github.aint.jblog.model.entity.News;
import com.github.aint.jblog.model.entity.PublicMessage;
import com.github.aint.jblog.model.entity.User;

/**
 * @author Olexandr Tyshkovets
 */
public final class EntityFactory {

    private EntityFactory() {
    }

    public static User getDefaultUser(String userName, String email) {
        User user = new User(userName, email, "password");
        user.setActivated(true);
        user.setBirthday(new Date());
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setRegistrationDate(new Date());
        user.setSalt("salt");
        return user;
    }

    public static Article getDefaultArticle(User author) {
        Article article = new Article("title", "preview", "body", "keywords", author);
        article.setCreationDate(new Date());
        article.setRating(0);
        return article;
    }

    public static Comment getDefaultComment(Article article, User author) {
        Comment comment = new Comment("body", article, author);
        comment.setCreationDate(new Date());
        comment.setRating(0);
        return comment;
    }

    public static PublicMessage getDefaultPublicMessage() {
        PublicMessage pm = new PublicMessage("body", "authorName");
        pm.setCreationDate(new Date());
        return pm;
    }

    public static News getDefaultNews(User author) {
        News news = new News("title", "body", author);
        news.setCreationDate(new Date());
        return news;
    }

}
