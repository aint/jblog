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
package com.github.aint.jblog.service.validation.dto;

import java.util.HashSet;
import java.util.Set;

import com.github.aint.jblog.model.entity.AbstractArticle;
import com.github.aint.jblog.model.entity.News;
import com.github.aint.jblog.model.entity.NewsImportance;
import com.github.aint.jblog.service.util.HtmlTag;
import com.github.aint.jblog.service.util.StringUtil;
import com.github.aint.jblog.service.validation.annotation.Length;
import com.github.aint.jblog.service.validation.annotation.ValidField;
import com.github.aint.jblog.service.validation.impl.AnnotationBasedValidator;

/**
 * DTO for a {@link News} entity. Used for validation of a {@link News}'s data.
 * 
 * @author Olexandr Tyshkovets
 * @see ValidField
 * @see Length
 * @see AnnotationBasedValidator
 */
public class NewsDto {
    @ValidField(length = @Length(min = AbstractArticle.TITLE_MIN_LENGTH, max = AbstractArticle.TITLE_MAX_LENGTH))
    private String title;
    @ValidField(length = @Length(min = News.NEWS_BODY_MIN_LENGTH, max = News.NEWS_BODY_MAX_LENGTH))
    private String body;
    @ValidField(regex = "(minor|intermediate|major)")
    private String importance;

    /**
     * Constructs a {@code NewsDto} with the given parameters. Trims all not-null fields.
     * 
     * @param title
     *            the news's title
     * @param body
     *            the news's body
     * @param importance
     *            the news's importance
     */
    public NewsDto(String title, String body, String importance) {
        this.title = title == null ? null : title.trim();
        this.body = body == null ? null : body.trim();
        this.importance = importance == null ? null : importance.trim();
    }

    /**
     * Creates a {@link News}. Escapes HTML in the news's {@code body} and converts the {@code body}'s text's line
     * delimiters to HTML {@code <br>} tags.
     * 
     * @return a created {@link News}.
     * @see StringUtil#escapeHtmlInText(Set, String)
     * @see StringUtil#convertLineDelimitersToHtmlBR(String)
     */
    public News createNews() {
        Set<HtmlTag> ignoredTags = new HashSet<HtmlTag>();
        ignoredTags.add(HtmlTag.A);
        ignoredTags.add(HtmlTag.B);
        ignoredTags.add(HtmlTag.BLOCKQUOTE);
        ignoredTags.add(HtmlTag.DEL);
        ignoredTags.add(HtmlTag.I);
        News news = new News(title, StringUtil.convertLineDelimitersToHtmlBR(
                StringUtil.escapeHtmlInText(ignoredTags, body)), null);
        news.setNewsImportance(NewsImportance.lookUp(importance));
        return news;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

}
