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

import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.service.util.EntityComparatorFactory;

/**
 * This class provides a {@link Article}'s comparators which sort by time, id, name and rating.
 * 
 * @author Olexandr Tyshkovets
 * @see EntityComparatorFactory
 */
public class ArticleComparatorFactory implements EntityComparatorFactory<Article> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<Article> getDateComparator() {
        Comparator<Article> comparator = new Comparator<Article>() {
            @Override
            public int compare(Article a1, Article a2) {
                return a1.getCreationDate().compareTo(a2.getCreationDate());
            }
        };
        return comparator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<Article> getIdComparator() {
        Comparator<Article> comparator = new Comparator<Article>() {
            @Override
            public int compare(Article a1, Article a2) {
                return a1.getId().compareTo(a2.getId());
            }
        };
        return comparator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<Article> getNameComparator() {
        Comparator<Article> comparator = new Comparator<Article>() {
            @Override
            public int compare(Article a1, Article a2) {
                return a1.getTitle().compareTo(a2.getTitle());
            }
        };
        return comparator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<Article> getRatingComparator() {
        Comparator<Article> comparator = new Comparator<Article>() {
            @Override
            public int compare(Article a1, Article a2) {
                return a1.getRating() - a2.getRating();
            }
        };
        return comparator;
    }

}
