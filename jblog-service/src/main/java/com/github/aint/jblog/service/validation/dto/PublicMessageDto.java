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

import com.github.aint.jblog.model.entity.PublicMessage;
import com.github.aint.jblog.service.util.HtmlTag;
import com.github.aint.jblog.service.util.StringUtil;
import com.github.aint.jblog.service.validation.annotation.Length;
import com.github.aint.jblog.service.validation.annotation.ValidField;
import com.github.aint.jblog.service.validation.impl.AnnotationBasedValidator;

/**
 * DTO for a {@link PublicMessage} entity. Used for validation of a {@link PublicMessage}'s data.
 * 
 * @author Olexandr Tyshkovets
 * @see ValidField
 * @see Length
 * @see AnnotationBasedValidator
 */
public class PublicMessageDto {
    @ValidField(length = @Length(min = PublicMessage.AUTHOR_NAME_MIN_LENGTH, max = PublicMessage.AUTHOR_NAME_MAX_LENGTH),
            regex = "[а-яґєіїёА-ЯҐЄІЇЁ\\w]", fieldName = "author name")
    private String authorName;
    @ValidField(length = @Length(min = PublicMessage.PUBLIC_MESSAGE_BODY_MIN_LENGTH,
            max = PublicMessage.PUBLIC_MESSAGE_BODY_MAX_LENGTH))
    private String body;

    /**
     * Constructs a {@code PublicMessageDto} with the given parameters. Trims all not-null fields.
     * 
     * @param authorName
     *            the publicMessage's authorName
     * @param body
     *            the publicMessage's body
     */
    public PublicMessageDto(String authorName, String body) {
        this.authorName = authorName == null ? null : authorName.trim();
        this.body = body == null ? null : body.trim();
    }

    /**
     * Creates a {@link PublicMessage}. Escapes HTML in the public message's {@code body} and converts the {@code body}
     * 's text's line delimiters to HTML {@code <br>} tags.
     * 
     * @return a created {@link PublicMessage}
     * @see StringUtil#escapeHtmlInText(Set, String)
     * @see StringUtil#convertLineDelimitersToHtmlBR(String)
     */
    public PublicMessage createPublicMessage() {
        Set<HtmlTag> ignoredTags = new HashSet<HtmlTag>();
        ignoredTags.add(HtmlTag.A);
        ignoredTags.add(HtmlTag.B);
        ignoredTags.add(HtmlTag.BLOCKQUOTE);
        ignoredTags.add(HtmlTag.DEL);
        ignoredTags.add(HtmlTag.I);
        return new PublicMessage(StringUtil.convertLineDelimitersToHtmlBR(
                StringUtil.escapeHtmlInText(ignoredTags, body)), authorName);
    }

    /**
     * @return the authorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

}
