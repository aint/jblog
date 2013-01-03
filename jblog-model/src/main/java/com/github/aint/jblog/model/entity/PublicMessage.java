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
import javax.persistence.Table;

/**
 * This class stores the information about a public message.
 * 
 * @author Olexandr Tyshkovets
 * @see Entity
 * @see AbstractTextEntity
 */
@javax.persistence.Entity
@Table(name = "PUBLIC_MESSAGE")
public class PublicMessage extends AbstractTextEntity implements Entity, Comparable<PublicMessage> {
    private static final long serialVersionUID = -4838968789838901557L;
    private Long id;
    private String authorName;

    /**
     * The minimum length of the public message's author's name.
     */
    public static final int AUTHOR_NAME_MIN_LENGTH = 1;
    /**
     * The maximum length of the public message's author's name.
     */
    public static final int AUTHOR_NAME_MAX_LENGTH = 20;
    /**
     * The minimum length of the public message's body.
     */
    public static final int PUBLIC_MESSAGE_BODY_MIN_LENGTH = 2;
    /**
     * The maximum length of the public message's body.
     */
    public static final int PUBLIC_MESSAGE_BODY_MAX_LENGTH = 5000;

    /**
     * Default constructor for hibernate.
     */
    protected PublicMessage() {
    }

    /**
     * Constructs a {@code PublicMessage} with required fields.
     * 
     * @param body
     *            the public message's body
     * @param authorName
     *            the public message's author's name
     */
    public PublicMessage(String body, String authorName) {
        this.body = body;
        this.authorName = authorName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Id
    @GeneratedValue
    @Column(name = "PUBLIC_MESSAGE_ID")
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
     * Compares two public messages for ordering. Compares by {@code body}, {@code author} and {@code creationDate}.
     * 
     * @param message
     *            the public message to be compared
     * @return a negative integer, zero, or a positive integer as this public message is less than, equal to, or greater
     *         than the specified {@code message}
     * @throws NullPointerException
     *             if the specified {@code message} is null
     */
    @Override
    public int compareTo(PublicMessage message) {
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
     * Compares two public messages for equality. Returns {@code true} if and only if the argument is not null and is a
     * {@code PublicMessage} object that has same {@code body}, {@code author} and {@code creationDate}.
     * 
     * @param obj
     *            the reference object with which to compare
     * @return {@code true} if this object is the same as the {@code obj} argument; {@code false} otherwise
     * @see PublicMessage#hashCode
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PublicMessage)) {
            return false;
        }
        PublicMessage message = (PublicMessage) obj;
        return (body == null ? message.body == null : body.equals(message.body)) &&
                (authorName == null ? message.authorName == null : authorName.equals(message.authorName)) &&
                (creationDate == null ? message.creationDate == null : creationDate.equals(message.creationDate));
    }

    /**
     * Returns a hash code value for this object. For result calculation used {@code body}, {@code author} and
     * {@code creationDate}.
     * 
     * @return a hash code value for this object
     * @see PublicMessage#equals(Object)
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
     * Returns a string representation of this public message. This is just the string representation of all fields.
     * 
     * @return a string representation of this public message
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
