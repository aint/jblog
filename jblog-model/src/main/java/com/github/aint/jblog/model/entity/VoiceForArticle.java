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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * This class stores the information about a voice for an {@link Article}.
 * 
 * @author Olexandr Tyshkovets
 * @see AbstractVoice
 */
@Entity
@Table(name = "VOICE_FOR_ARTICLE")
@NamedQueries({
        @NamedQuery(name = "VoiceForArticle.getAll", query = "FROM VoiceForArticle"),
        @NamedQuery(name = "VoiceForArticle.getAllOnPageAsc", query = "FROM VoiceForArticle v ORDER BY v.id ASC"),
        @NamedQuery(name = "VoiceForArticle.getAllOnPageDesc", query = "FROM VoiceForArticle v ORDER BY v.id DESC"),
        @NamedQuery(name = "VoiceForArticle.getCount", query = "SELECT COUNT(*) FROM VoiceForArticle"),
        @NamedQuery(name = "VoiceForArticle.deleteById", query = "DELETE VoiceForArticle v WHERE v.id = ?"),
})
public class VoiceForArticle extends AbstractVoice {
    private static final long serialVersionUID = 8782354428056517207L;
    private Long id;
    private Article article;

    /**
     * Default constructor for hibernate.
     */
    protected VoiceForArticle() {
    }

    /**
     * Constructs a {@code VoiceForArticle} with required fields.
     * 
     * @param value
     *            the voice's value
     * @param article
     *            the voice's article
     * @param author
     *            the voice's author
     * @see Article
     * @see User
     * @see VoiceValue
     */
    public VoiceForArticle(VoiceValue value, Article article, User author) {
        this.value = value;
        this.article = article;
        this.author = author;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Id
    @GeneratedValue
    @Column(name = "VOICE_FOR_ARTICLE_ID")
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
     * Compares two voices for equality. Returns {@code true} if and only if the argument is not null and is a
     * {@code VoiceForArticle} object that has same {@code value}, {@code article} and {@code author}.
     * 
     * @param obj
     *            the reference object with which to compare
     * @return {@code true} if this object is the same as the {@code obj} argument; {@code false} otherwise
     * @see VoiceForArticle#hashCode
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VoiceForArticle)) {
            return false;
        }
        VoiceForArticle voice = (VoiceForArticle) obj;
        return (value == null ? voice.value == null : value.equals(voice.value)) &&
                (article == null ? voice.article == null : article.equals(voice.article)) &&
                (author == null ? voice.author == null : author.equals(voice.author));
    }

    /**
     * Returns a hash code value for this object. For result calculation used {@code value}, {@code article} and
     * {@code author}.
     * 
     * @return a hash code value for this object
     * @see VoiceForArticle#equals(Object)
     */
    @Override
    public int hashCode() {
        int result = 13;
        result = 37 * result + value.hashCode();
        result = 37 * result + article.hashCode();
        result = 37 * result + author.hashCode();
        return result;
    }

    /**
     * Returns a string representation of this voice for article. This is just the string representation of all fields.
     * 
     * @return a string representation of this article
     */
    @Override
    public String toString() {
        return getClass().getName() +
                "[id=" + id
                + ",value=" + value
                + ",article=" + article
                + ",author=" + author
                + "]";
    }

}
