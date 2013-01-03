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

import com.github.aint.jblog.model.entity.Comment;
import com.github.aint.jblog.service.util.HtmlTag;
import com.github.aint.jblog.service.util.StringUtil;
import com.github.aint.jblog.service.validation.annotation.Length;
import com.github.aint.jblog.service.validation.annotation.ValidField;
import com.github.aint.jblog.service.validation.impl.AnnotationBasedValidator;

/**
 * DTO for a {@link Comment} entity. Used for validation of a {@link Comment}'s data.
 * 
 * @author Olexandr Tyshkovets
 * @see ValidField
 * @see Length
 * @see AnnotationBasedValidator
 */
public class CommentDto {
    @ValidField(length = @Length(min = Comment.COMMENT_BODY_MIN_LENGTH,
            max = Comment.COMMENT_BODY_MAX_LENGTH))
    private final String body;

    /**
     * Constructs a {@code CommentDto} with the given parameter. Trims a {@code body} if it is not-null.
     * 
     * @param body
     *            the comment's body
     */
    public CommentDto(String body) {
        this.body = body == null ? null : body.trim();
    }

    /**
     * Creates a {@link Comment}. Escapes HTML in the public message's {@code body} and converts the {@code body}'s
     * text's line delimiters to HTML {@code <br>} tags.
     * 
     * @return a created {@link Comment}
     * @see StringUtil#processKeywords(String)
     * @see StringUtil#escapeHtmlInText(Set, String)
     * @see StringUtil#convertLineDelimitersToHtmlBR(String)
     */
    public Comment createComment() {
        Set<HtmlTag> ignoredTags = new HashSet<HtmlTag>();
        ignoredTags.add(HtmlTag.A);
        ignoredTags.add(HtmlTag.B);
        ignoredTags.add(HtmlTag.BLOCKQUOTE);
        ignoredTags.add(HtmlTag.DEL);
        ignoredTags.add(HtmlTag.I);
        return new Comment(StringUtil.convertLineDelimitersToHtmlBR(
                StringUtil.escapeHtmlInText(ignoredTags, body)), null, null);
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

}
