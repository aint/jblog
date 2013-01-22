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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * This class stores the information about an anonymous message.
 * 
 * @author Olexandr Tyshkovets
 * @see Entity
 * @see AbstractTextEntity
 */
@javax.persistence.Entity
@Table(name = "ANONYMOUS_MESSAGE")
@NamedQueries({
        @NamedQuery(name = "AnonymousMessage.getAll", query = "FROM AnonymousMessage"),
        @NamedQuery(name = "AnonymousMessage.getAllOnPageAsc", query = "FROM AnonymousMessage m ORDER BY m.id ASC"),
        @NamedQuery(name = "AnonymousMessage.getAllOnPageDesc", query = "FROM AnonymousMessage m ORDER BY m.id DESC"),
        @NamedQuery(name = "AnonymousMessage.getCount", query = "SELECT COUNT(*) FROM AnonymousMessage"),
        @NamedQuery(name = "AnonymousMessage.deleteById", query = "DELETE AnonymousMessage m WHERE m.id = ?"),
})
public class AnonymousMessage extends AbstractTextEntity implements Entity, Comparable<AnonymousMessage> {
    private static final long serialVersionUID = -4838968789838901557L;
    private Long id;
    private String authorName;

    /**
     * The minimum length of the anonymous message's author's name.
     */
    public static final int AUTHOR_NAME_MIN_LENGTH = 1;
    /**
     * The maximum length of the anonymous message's author's name.
     */
    public static final int AUTHOR_NAME_MAX_LENGTH = 20;
    /**
     * The minimum length of the anonymous message's body.
     */
    public static final int ANONYMOUS_MESSAGE_BODY_MIN_LENGTH = 2;
    /**
     * The maximum length of the anonymous message's body.
     */
    public static final int ANONYMOUS_MESSAGE_BODY_MAX_LENGTH = 5000;

    /**
     * Default constructor for hibernate.
     */
    protected AnonymousMessage() {
    }

    /**
     * Constructs a {@code AnonymousMessage} with required fields.
     * 
     * @param body
     *            the anonymous message's body
     * @param authorName
     *            the anonymous message's author's name
     */
    public AnonymousMessage(String body, String authorName) {
        this.body = body;
        this.authorName = authorName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Id
    @GeneratedValue
    @Column(name = "ANONYMOUS_MESSAGE_ID")
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
     * @return the authorName
     */
    @Column(name = "AUTHOR_NAME", nullable = false)
    public String getAuthorName() {
        return authorName;
    }

    /**
     * @param authorName
     *            the authorName to set
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * Compares two anonymous messages for ordering. Compares by {@code body}, {@code author} and {@code creationDate}.
     * 
     * @param message
     *            the anonymous message to be compared
     * @return a negative integer, zero, or a positive integer as this anonymous message is less than, equal to, or
     *         greater than the specified {@code message}
     * @throws NullPointerException
     *             if the specified {@code message} is {@code null}
     */
    @Override
    public int compareTo(AnonymousMessage message) {
        int result = body.compareTo(message.body);
        if (result != 0) {
            return result;
        }
        result = authorName.compareTo(message.authorName);
        if (result != 0) {
            return result;
        }
        result = creationDate.compareTo(message.creationDate);
        if (result != 0) {
            return result;
        }
        return 0;
    }

    /**
     * Compares two anonymous messages for equality. Returns {@code true} if and only if the argument is not
     * {@code null} and is a {@code AnonymousMessage} object that has same {@code body}, {@code author} and
     * {@code creationDate}.
     * 
     * @param obj
     *            the reference object with which to compare
     * @return {@code true} if this object is the same as the {@code obj} argument; {@code false} otherwise
     * @see AnonymousMessage#hashCode
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AnonymousMessage)) {
            return false;
        }
        AnonymousMessage message = (AnonymousMessage) obj;
        return (body == null ? message.body == null : body.equals(message.body)) &&
                (authorName == null ? message.authorName == null : authorName.equals(message.authorName)) &&
                (creationDate == null ? message.creationDate == null : creationDate.equals(message.creationDate));
    }

    /**
     * Returns a hash code value for this object. For result calculation used {@code body}, {@code author} and
     * {@code creationDate}.
     * 
     * @return a hash code value for this object
     * @see AnonymousMessage#equals(Object)
     */
    @Override
    public int hashCode() {
        int result = 13;
        result = 37 * result + body.hashCode();
        result = 37 * result + authorName.hashCode();
        result = 37 * result + creationDate.hashCode();
        return result;
    }

    /**
     * Returns a string representation of this anonymous message. This is just the string representation of all fields.
     * 
     * @return a string representation of this anonymous message
     */
    @Override
    public String toString() {
        return getClass().getName() +
                "[id=" + id
                + ",body=" + body
                + ",authorName=" + authorName
                + ",creationDate=" + creationDate
                + "]";
    }

}
