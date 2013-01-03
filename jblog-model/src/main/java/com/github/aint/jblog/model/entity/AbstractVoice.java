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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * This abstract class stores the general information about an voice.
 * 
 * @author Olexandr Tyshkovets
 * @see Entity
 * @see VoiceForArticle
 * @see VoiceForComment
 */
@MappedSuperclass
public abstract class AbstractVoice implements Entity {
    private static final long serialVersionUID = -2822748634840822972L;
    protected VoiceValue value;
    protected User author;

    /**
     * Default constructor for hibernate and the serialization mechanism.
     */
    protected AbstractVoice() {
    }

    /**
     * @return abstract voice value
     * @see VoiceValue
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "VALUE", nullable = false)
    public VoiceValue getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(VoiceValue value) {
        this.value = value;
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

}
