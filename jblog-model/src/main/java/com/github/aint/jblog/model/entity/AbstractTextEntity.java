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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * This abstract class stores the general information about a text entity.
 * 
 * @author Olexandr Tyshkovets
 * @see AbstractArticle
 * @see Comment
 * @see AnonymousMessage
 */
@MappedSuperclass
public abstract class AbstractTextEntity implements Entity {
    private static final long serialVersionUID = 6099691701942315542L;
    protected String body;
    protected Date creationDate;

    /**
     * Default constructor for hibernate and the serialization mechanism.
     */
    public AbstractTextEntity() {
    }

    /**
     * @return the body
     */
    @Column(name = "BODY", columnDefinition = "TEXT", nullable = false)
    public String getBody() {
        return body;
    }

    /**
     * @param body
     *            the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return the creationDate
     */
    @Column(name = "CREATION_DATE", columnDefinition = "DATETIME", nullable = false)
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate
     *            the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
