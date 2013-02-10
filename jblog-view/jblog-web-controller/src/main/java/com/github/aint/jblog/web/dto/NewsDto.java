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
package com.github.aint.jblog.web.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.github.aint.jblog.model.entity.AbstractArticle;
import com.github.aint.jblog.model.entity.Importance;
import com.github.aint.jblog.model.entity.News;
import com.github.aint.jblog.service.util.HtmlTag;
import com.github.aint.jblog.service.util.StringUtil;

/**
 * DTO for a {@link News} entity. Used for validation of a {@link News}'s data.
 * 
 * @author Olexandr Tyshkovets
 */
public class NewsDto {

    @NotNull(message = "{news.title.not_null}")
    @Size(min = AbstractArticle.TITLE_MIN_LENGTH, max = AbstractArticle.TITLE_MAX_LENGTH, message = "{news.title.length}")
    private String title;

    @NotNull(message = "{news.body.not_null}")
    @Size(min = News.NEWS_BODY_MIN_LENGTH, max = News.NEWS_BODY_MAX_LENGTH, message = "{news.body.length}")
    private String body;

    private String pinned;

    @Pattern(regexp = "(low|middle|high)", message = "{news.importance.pattern}")
    private String importance;

    /**
     * Constructs a {@code NewsDto} with the given parameters. Trims all not-null fields.
     * 
     * @param title
     *            the news' title
     * @param body
     *            the news' body
     * @param pinned
     *            the news' pinned status
     * @param importance
     *            the news' importance
     */
    public NewsDto(String title, String body, String pinned, String importance) {
        this.title = title == null ? null : title.trim();
        this.body = body == null ? null : body.trim();
        this.pinned = pinned == null ? null : pinned.trim();
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
        News news = new News(
                title,
                StringUtil.convertLineDelimitersToHtmlBR(StringUtil.escapeHtmlInText(ignoredTags, body)),
                "true".equals(pinned),
                Importance.lookUp(importance));
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

    /**
     * @return the pinned
     */
    public String getPinned() {
        return pinned;
    }

    /**
     * @return the importance
     */
    public String getImportance() {
        return importance;
    }

}
