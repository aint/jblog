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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * This abstract class stores the general information about an article.
 * 
 * @author Olexandr Tyshkovets
 * @see Article
 * @see News
 */
@MappedSuperclass
public abstract class AbstractArticle extends AbstractTextEntity {
    private static final long serialVersionUID = 225477695258912797L;
    protected String title;
    protected User author;

    /**
     * The minimum length of the abstract article's title.
     */
    public static final int TITLE_MIN_LENGTH = 5;
    /**
     * The maximum length of the abstract article's title.
     */
    public static final int TITLE_MAX_LENGTH = 75;

    /**
     * Default constructor for hibernate and the serialization mechanism.
     */
    protected AbstractArticle() {
    }

    /**
     * @return the title
     */
    @Column(name = "TITLE", nullable = false)
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the author
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

}
