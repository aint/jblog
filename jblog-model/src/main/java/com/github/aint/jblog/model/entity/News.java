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
package com.github.aint.jblog.model.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class stores the information about a news.
 * 
 * @author Olexandr Tyshkovets
 * @see Entity
 */
@javax.persistence.Entity
@Table(name = "NEWS")
public class News extends AbstractArticle {
    private static final long serialVersionUID = -5769938854957361379L;
    private Long id;
    private NewsImportance newsImportance = NewsImportance.INTERMEDIATE;

    /**
     * The minimum length of the news' body.
     */
    public static final int NEWS_BODY_MIN_LENGTH = 10;
    /**
     * The maximum length of the news' body.
     */
    public static final int NEWS_BODY_MAX_LENGTH = 2000;

    /**
     * Default constructor for hibernate.
     */
    protected News() {
    }

    /**
     * Constructs a {@code News} with required fields.
     * 
     * @param title
     *            a news's title
     * @param body
     *            a news's body
     * @param author
     *            a news's author
     */
    public News(String title, String body, User author) {
        this.title = title;
        this.body = body;
        this.author = author;
    }

    /**
     * {@inheritDoc}
     */
    @Id
    @GeneratedValue
    @Column(name = "NEWS_ID")
    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the newsImportance
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "IMPORTANCE", nullable = false)
    public NewsImportance getNewsImportance() {
        return newsImportance;
    }

    /**
     * @param newsImportance
     *            the newsImportance to set
     */
    public void setNewsImportance(NewsImportance newsImportance) {
        this.newsImportance = newsImportance;
    }

    /**
     * Compares two news for equality. Returns {@code true} if and only if the argument is not {@code null} and is a
     * {@code News} object that has same {@code title}, {@code body}, {@code creationDate} and {@code author}.
     * 
     * @param obj
     *            the reference object with which to compare
     * @return {@code true} if this object is the same as the {@code obj} argument; {@code false} otherwise
     * @see News#hashCode
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof News)) {
            return false;
        }
        News news = (News) obj;
        return (id == null ? news.id == null : id.equals(news.id)) &&
                (body == null ? news.body == null : body.equals(news.body)) &&
                (creationDate == null ? news.creationDate == null : creationDate.equals(news.creationDate)) &&
                (author == null ? news.author == null : author.equals(news.author));

    }

    /**
     * Returns a hash code value for this object. For result calculation used {@code title}, {@code body},
     * {@code creationDate} and {@code author}.
     * 
     * @return a hash code value for this object
     * @see News#equals(Object)
     */
    @Override
    public int hashCode() {
        int result = 13;
        result = 37 * result + title.hashCode();
        result = 37 * result + body.hashCode();
        result = 37 * result + creationDate.hashCode();
        result = 37 * result + author.hashCode();
        return result;
    }

    /**
     * Returns a string representation of this article. This is just the string representation of all fields.
     * 
     * @return a string representation of this news
     */
    @Override
    public String toString() {
        return getClass().getName() +
                "[id=" + id
                + ",title=" + title
                + ",body=" + body
                + ",creationDate=" + creationDate
                + ",author=" + author.getUserName()
                + "]";
    }

}
