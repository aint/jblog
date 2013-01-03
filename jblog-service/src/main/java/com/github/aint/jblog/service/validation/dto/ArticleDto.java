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
import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.service.util.HtmlTag;
import com.github.aint.jblog.service.util.StringUtil;
import com.github.aint.jblog.service.validation.annotation.Length;
import com.github.aint.jblog.service.validation.annotation.ValidField;
import com.github.aint.jblog.service.validation.impl.AnnotationBasedValidator;

/**
 * DTO for a {@link Article} entity. Used for validation of a {@link Article}'s data.
 * 
 * @author Olexandr Tyshkovets
 * @see ValidField
 * @see Length
 * @see AnnotationBasedValidator
 */
public class ArticleDto {
    @ValidField(length = @Length(min = AbstractArticle.TITLE_MIN_LENGTH, max = AbstractArticle.TITLE_MAX_LENGTH))
    private String title;
    @ValidField(length = @Length(min = Article.ARTICLE_PREVIEW_MIN_LENGTH, max = Article.ARTICLE_PREVIEW_MAX_LENGTH))
    private String preview;
    @ValidField(length = @Length(min = Article.ARTICLE_BODY_MIN_LENGTH, max = Article.ARTICLE_BODY_MAX_LENGTH))
    private String body;
    @ValidField(length = @Length(min = Article.ARTICLE_KEYWORDS_MIN_LENGTH, max = Article.ARTICLE_KEYWORDS_MAX_LENGTH))
    private String keywords;

    /**
     * Constructs a {@code ArticleDto} with the given parameters. Trims all not-null fields.
     * 
     * @param title
     *            the article's title
     * @param preview
     *            the article's preview
     * @param body
     *            the article's body
     * @param keywords
     *            the article's keywords
     * @see Article
     */
    public ArticleDto(String title, String preview, String body, String keywords) {
        this.title = title == null ? null : title.trim();
        this.preview = preview == null ? preview : preview.trim();
        this.body = body == null ? null : body.trim();
        this.keywords = keywords == null ? null : keywords.trim();
    }

    /**
     * Creates a {@link Article}. Process the article's keywords, escapes HTML in the article's {@code body} and
     * converts the {@code body}'s text's line delimiters to HTML {@code <br>} tags.
     * 
     * @return a created {@link Article}.
     * @see StringUtil#processKeywords(String)
     * @see StringUtil#escapeHtmlInText(Set, String)
     * @see StringUtil#convertLineDelimitersToHtmlBR(String)
     */
    public Article createArticle() {
        Set<HtmlTag> ignoredTags = new HashSet<HtmlTag>();
        ignoredTags.add(HtmlTag.A);
        ignoredTags.add(HtmlTag.B);
        ignoredTags.add(HtmlTag.BLOCKQUOTE);
        ignoredTags.add(HtmlTag.DEL);
        ignoredTags.add(HtmlTag.I);
        return new Article(title, preview, StringUtil.convertLineDelimitersToHtmlBR(
                StringUtil.escapeHtmlInText(ignoredTags, body)), StringUtil.processKeywords(keywords));
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the preview
     */
    public String getPreview() {
        return preview;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @return the keyWords
     */
    public String getKeywords() {
        return keywords;
    }

}
