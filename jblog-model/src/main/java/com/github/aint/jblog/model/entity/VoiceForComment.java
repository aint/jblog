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
import javax.persistence.Table;

/**
 * Stores the information about a voice for a {@link Comment}.
 * 
 * @author Olexandr Tyshkovets
 * @see AbstractVoice
 */
@Entity
@Table(name = "VOICE_FOR_COMMENT")
public class VoiceForComment extends AbstractVoice {
    private static final long serialVersionUID = 7650731511739362503L;
    private Long id;
    private Comment comment;

    /**
     * Default constructor for hibernate.
     */
    protected VoiceForComment() {
    }

    /**
     * Constructs a {@code VoiceForComment} with required fields.
     * 
     * @param value
     *            the voice's value
     * @param comment
     *            the voice's comment
     * @param author
     *            the voice's author
     * @see Comment
     * @see User
     * @see VoiceValue
     */
    public VoiceForComment(VoiceValue value, Comment comment, User author) {
        this.value = value;
        this.author = author;
        this.comment = comment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Id
    @GeneratedValue
    @Column(name = "VOICE_FOR_COMMENT_ID")
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
     * @return the comment
     * @see Comment
     */
    @ManyToOne
    @JoinColumn(name = "FK_COMMENT", nullable = false)
    public Comment getComment() {
        return comment;
    }

    /**
     * @param comment
     *            the comment to set
     */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * Compares two voices for equality. Returns {@code true} if and only if the argument is not null and is a
     * {@code VoiceForComment} object that has same {@code value}, {@code comment} and {@code author}.
     * 
     * @param obj
     *            the reference object with which to compare
     * @return {@code true} if this object is the same as the {@code obj} argument; {@code false} otherwise
     * @see VoiceForComment#hashCode
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VoiceForComment)) {
            return false;
        }
        VoiceForComment voice = (VoiceForComment) obj;
        return (value == null ? voice.value == null : value.equals(voice.value)) &&
                (comment == null ? voice.comment == null : comment.equals(voice.comment)) &&
                (author == null ? voice.author == null : author.equals(voice.author));
    }

    /**
     * Returns a hash code value for this object. For result calculation used {@code value}, {@code comment} and
     * {@code author}.
     * 
     * @return a hash code value for this object
     * @see VoiceForComment#equals(Object)
     */
    @Override
    public int hashCode() {
        int result = 13;
        result = 37 * result + value.hashCode();
        result = 37 * result + comment.hashCode();
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
                + ",comment=" + comment
                + ",author=" + author
                + "]";
    }

}
