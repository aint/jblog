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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class stores the information about a comment.
 * 
 * @author Olexandr Tyshkovets
 * @see Entity
 * @see AbstractTextEntity
 */
@javax.persistence.Entity
@Table(name = "COMMENT")
public class Comment extends AbstractTextEntity implements Entity, Comparable<Comment> {
    private static final long serialVersionUID = 1652136605202413471L;
    private Long id;
    private int rating;
    private Article article;
    private User author;
    private Set<VoiceForComment> voices = new HashSet<VoiceForComment>();

    /**
     * The minimum length of the comment's body.
     */
    public static final int COMMENT_BODY_MIN_LENGTH = 2;
    /**
     * The maximum length of the comment's body.
     */
    public static final int COMMENT_BODY_MAX_LENGTH = 5000;

    /**
     * Default constructor for hibernate.
     */
    protected Comment() {
    }

    /**
     * Constructs a {@code Comment} with required fields.
     * 
     * @param body
     *            the comment's body
     * @param article
     *            the comment's article
     * @param author
     *            the comment's author
     * @see Article
     * @see User
     */
    public Comment(String body, Article article, User author) {
        this.body = body;
        this.article = article;
        this.author = author;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Id
    @GeneratedValue
    @Column(name = "COMMENT_ID")
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
     * @return the rating
     */
    @Column(name = "RATING", nullable = false)
    public int getRating() {
        return rating;
    }

    /**
     * @param rating
     *            the rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * @return the article
     * @see Article
     */
    @ManyToOne
    @JoinColumn(name = "FK_ARTICLE", nullable = false)
    public Article getArticle() {
        return article;
    }

    /**
     * @param article
     *            the article to set
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     * @return the author
     * @see User
     */
    @ManyToOne
    @JoinColumn(name = "FK_USER", nullable = false)
    public User getAuthor() {
        return author;
    }

    /**
     * @param author
     *            the author to set
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * @return the voices
     */
    @OneToMany(mappedBy = "comment")
    public Set<VoiceForComment> getVoices() {
        return voices;
    }

    /**
     * @param voices
     *            the voices to set
     */
    public void setVoices(Set<VoiceForComment> voices) {
        this.voices = voices;
    }

    /**
     * Compares two comments for ordering. Compares by {@code body}, {@code article}, {@code author} and
     * {@code creationDate}.
     * 
     * @param comment
     *            the comment to be compared
     * @return a negative integer, zero, or a positive integer as this comment is less than, equal to, or greater than
     *         the specified {@code comment}
     * @throws NullPointerException
     *             if the specified {@code comment} is null
     */
    @Override
    public int compareTo(Comment comment) {
        int result = body.compareTo(comment.body);
        if (result != 0) {
            return result;
        }
        result = article.compareTo(comment.article);
        if (result != 0) {
            return result;
        }
        result = author.compareTo(comment.author);
        if (result != 0) {
            return result;
        }
        result = creationDate.compareTo(comment.creationDate);
        if (result != 0) {
            return result;
        }
        return 0;
    }

    /**
     * Compares two comments for equality. Returns {@code true} if and only if the argument is not null and is a
     * {@code Comment} object that has same {@code body}, {@code article}, {@code author} and {@code creationDate}.
     * 
     * @param obj
     *            the reference object with which to compare
     * @return {@code true} if this object is the same as the {@code obj} argument; {@code false} otherwise
     * @see Comment#hashCode
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Comment)) {
            return false;
        }
        Comment comment = (Comment) obj;
        return (body == null ? comment.body == null : body.equals(comment.body)) &&
                (article == null ? comment.article == null : article.equals(comment.article)) &&
                (author == null ? comment.author == null : author.equals(comment.author)) &&
                (creationDate == null ? comment.creationDate == null : creationDate.equals(comment.creationDate));
    }

    /**
     * Returns a hash code value for this object. For result calculation used {@code body}, {@code article},
     * {@code author} and {@code creationDate}.
     * 
     * @return a hash code value for this object
     * @see Comment#equals(Object)
     */
    @Override
    public int hashCode() {
        int result = 13;
        result = 37 * result + body.hashCode();
        result = 37 * result + article.hashCode();
        result = 37 * result + author.hashCode();
        result = 37 * result + creationDate.hashCode();
        return result;
    }

    /**
     * Returns a string representation of this comment. This is just the string representation of all fields.
     * 
     * @return a string representation of this comment
     */
    @Override
    public String toString() {
        return getClass().getName() +
                "[id=" + id
                + ",body=" + body
                + ",rating=" + rating
                + ",article=" + article
                + ",author=" + author
                + ",creationDate=" + creationDate
                + "]";
    }

}
