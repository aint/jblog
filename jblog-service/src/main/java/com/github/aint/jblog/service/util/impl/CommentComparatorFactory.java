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
package com.github.aint.jblog.service.util.impl;

import java.util.Comparator;

import com.github.aint.jblog.model.entity.Comment;
import com.github.aint.jblog.service.util.EntityComparatorFactory;

/**
 * This class provides a {@link Comment}'s comparators which sort by time, id and rating.
 * 
 * @author Olexandr Tyshkovets
 * @see EntityComparatorFactory
 */
public class CommentComparatorFactory implements EntityComparatorFactory<Comment> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<Comment> getDateComparator() {
        Comparator<Comment> comparator = new Comparator<Comment>() {
            @Override
            public int compare(Comment c1, Comment c2) {
                return c1.getCreationDate().compareTo(c2.getCreationDate());
            }
        };
        return comparator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<Comment> getIdComparator() {
        Comparator<Comment> comparator = new Comparator<Comment>() {
            @Override
            public int compare(Comment c1, Comment c2) {
                return c1.getId().compareTo(c2.getId());
            }
        };
        return comparator;
    }

    /**
     * This method not implemented because the {@code Comment} doesn't have the name.
     * 
     * @throws UnsupportedOperationException
     *             always
     */
    @Override
    public Comparator<Comment> getNameComparator() {
        throw new UnsupportedOperationException("comment hasn't the name");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<Comment> getRatingComparator() {
        Comparator<Comment> comparator = new Comparator<Comment>() {
            @Override
            public int compare(Comment c1, Comment c2) {
                return c1.getRating() - c2.getRating();
            }
        };
        return comparator;
    }

}
