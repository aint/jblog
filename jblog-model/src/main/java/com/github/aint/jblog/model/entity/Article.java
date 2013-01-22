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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class stores the information about an article.
 * 
 * @author Olexandr Tyshkovets
 * @see AbstractArticle
 */
@javax.persistence.Entity
@Table(name = "ARTICLE")
@NamedQueries({
        @NamedQuery(name = "Article.getMostPopular", query = "FROM Article a WHERE a.rating >= ?"),
        @NamedQuery(name = "Article.getAll", query = "FROM Article"),
        @NamedQuery(name = "Article.getAllOnPageAsc", query = "FROM Article a ORDER BY a.id ASC"),
        @NamedQuery(name = "Article.getAllOnPageDesc", query = "FROM Article a ORDER BY a.id DESC"),
        @NamedQuery(name = "Article.getCount", query = "SELECT COUNT(*) FROM Article"),
        @NamedQuery(name = "Article.deleteById", query = "DELETE Article a WHERE a.id = ?"),
})
public class Article extends AbstractArticle implements Comparable<Article> {
    private static final long serialVersionUID = -7631829435790815969L;
    private Long id;
    private String preview;
    private String keywords;
    private int rating;
    private int views;
    private Set<Comment> comments = new HashSet<Comment>();
    private Set<VoiceForArticle> voices = new HashSet<VoiceForArticle>();

    /**
     * The minimum length of the article's preview.
     */
    public static final int ARTICLE_PREVIEW_MIN_LENGTH = 100;
    /**
     * The maximum length of the article's preview.
     */
    public static final int ARTICLE_PREVIEW_MAX_LENGTH = 750;
    /**
     * The minimum length of the article's body.
     */
    public static final int ARTICLE_BODY_MIN_LENGTH = 1500;
    /**
     * The maximum length of the article's body.
     */
    public static final int ARTICLE_BODY_MAX_LENGTH = 2000000000;
    /**
     * The minimum length of the article's keywords.
     */
    public static final int ARTICLE_KEYWORDS_MIN_LENGTH = 5;
    /**
     * The maximum length of the article's keywords.
     */
    public static final int ARTICLE_KEYWORDS_MAX_LENGTH = 115;

    /**
     * Default constructor for hibernate.
     */
    protected Article() {
    }

    /**
     * Constructs an {@code Article} with required fields.
     * 
     * @param title
     *            an article's title
     * @param preview
     *            an article's preview
     * @param body
     *            an article's body
     * @param keywords
     *            an article's keywords
     */
    public Article(String title, String preview, String body, String keywords) {
        this.title = title;
        this.preview = preview;
        this.body = body;
        this.keywords = keywords;
    }

    /**
     * Constructs an {@code Article} with required fields.
     * 
     * @param title
     *            an article's title
     * @param preview
     *            an article's preview
     * @param body
     *            an article's body
     * @param keywords
     *            an article's keywords
     * @param author
     *            an article's author
     * @see User
     */
    public Article(String title, String preview, String body, String keywords, User author) {
        this.title = title;
        this.preview = preview;
        this.body = body;
        this.keywords = keywords;
        this.author = author;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Id
    @GeneratedValue
    @Column(name = "ARTICLE_ID")
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
     * @return the preview
     */
    @Column(name = "PREVIEW", columnDefinition = "TEXT", nullable = false)
    public String getPreview() {
        return preview;
    }

    /**
     * @param preview
     *            the preview to set
     */
    public void setPreview(String preview) {
        this.preview = preview;
    }

    /**
     * @return the keywords
     */
    @Column(name = "KEYWORDS", nullable = false)
    public String getKeywords() {
        return keywords;
    }

    /**
     * @param keywords
     *            the keywords to set
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
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
     * @return the views
     */
    @Column(name = "VIEWS", nullable = false)
    public int getViews() {
        return views;
    }

    /**
     * @param views
     *            the views to set
     */
    public void setViews(int views) {
        this.views = views;
    }

    /**
     * @return set comments of article
     * @see Comment
     */
    @OneToMany(mappedBy = "article")
    public Set<Comment> getComments() {
        return comments;
    }

    /**
     * @param comments
     *            comments of article
     */
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    /**
     * @return set voices of article
     * @see VoiceForArticle
     */
    @OneToMany(mappedBy = "article")
    public Set<VoiceForArticle> getVoices() {
        return voices;
    }

    /**
     * @param voices
     *            voices of article
     */
    public void setVoices(Set<VoiceForArticle> voices) {
        this.voices = voices;
    }

    /**
     * Compares two articles for ordering. Compares by {@code title}, {@code body}, {@code creationDate} and
     * {@code author}.
     * 
     * @param article
     *            the article to be compared
     * @return a negative integer, zero, or a positive integer as this article is less than, equal to, or greater than
     *         the specified {@code article}
     * @throws NullPointerException
     *             if the specified {@code article} is null
     */
    @Override
    public int compareTo(Article article) {
        int result = title.compareTo(article.title);
        if (result != 0) {
            return result;
        }
        result = body.compareTo(article.body);
        if (result != 0) {
            return result;
        }
        result = creationDate.compareTo(article.creationDate);
        if (result != 0) {
            return result;
        }
        result = author.compareTo(article.author);
        if (result != 0) {
            return result;
        }
        return 0;
    }

    /**
     * Compares two articles for equality. Returns {@code true} if and only if the argument is not {@code null} and is a
     * {@code Article} object that has same {@code title}, {@code body}, {@code creationDate} and {@code author}.
     * 
     * @param obj
     *            the reference object with which to compare
     * @return {@code true} if this object is the same as the {@code obj} argument; {@code false} otherwise
     * @see Article#hashCode
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Article)) {
            return false;
        }
        Article article = (Article) obj;
        return (id == null ? article.id == null : id.equals(article.id)) &&
                (body == null ? article.body == null : body.equals(article.body)) &&
                (creationDate == null ? article.creationDate == null : creationDate.equals(article.creationDate)) &&
                (author == null ? article.author == null : author.equals(article.author));
    }

    /**
     * Returns a hash code value for this object. For result calculation used {@code title}, {@code body},
     * {@code creationDate} and {@code author}.
     * 
     * @return a hash code value for this object
     * @see Article#equals(Object)
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
     * Returns a string representation of this article. This is just the string representation of all fields and sizes
     * of collections. Instead of {@code preview}, {@code body} and {@code keywords} fields represents their length
     * because they can be too long.
     * 
     * @return a string representation of this article
     */
    @Override
    public String toString() {
        return getClass().getName() +
                "[id=" + id
                + ",title=" + title
                + ",preview.length()=" + preview.length()
                + ",body.length()=" + body.length()
                + ",keywords.length()=" + keywords.length()
                + ",rating=" + rating
                + ",creationDate=" + creationDate
                + ",author=" + author.getUserName()
                + ",comments.size()=" + comments.size()
                + ",voices.size()=" + voices.size()
                + "]";
    }

}
