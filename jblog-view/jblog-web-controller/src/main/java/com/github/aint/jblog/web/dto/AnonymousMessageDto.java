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

import com.github.aint.jblog.model.entity.AnonymousMessage;
import com.github.aint.jblog.service.util.HtmlTag;
import com.github.aint.jblog.service.util.StringUtil;
import com.github.aint.jblog.service.validation.annotation.FieldMatch;

/**
 * DTO for an {@link AnonymousMessage} entity. Used for validation of an {@link AnonymousMessage}'s data.
 * 
 * @author Olexandr Tyshkovets
 */
@FieldMatch(field = "captchaAnswer", matchField = "captcha", message = "{public_message.captcha.wrong}")
public class AnonymousMessageDto {

    @NotNull(message = "{public_message.author.not_null}")
    @Size(min = AnonymousMessage.AUTHOR_NAME_MIN_LENGTH, max = AnonymousMessage.AUTHOR_NAME_MAX_LENGTH, message = "{public_message.author.length}")
    @Pattern(regexp = "[а-яґєіїёА-ЯҐЄІЇЁ\\w]*", message = "{public_message.author.pattern}")
    private String authorName;

    @NotNull(message = "{public_message.body.not_null}")
    @Size(min = AnonymousMessage.ANONYMOUS_MESSAGE_BODY_MIN_LENGTH, max = AnonymousMessage.ANONYMOUS_MESSAGE_BODY_MAX_LENGTH, message = "{public_message.body.length}")
    private String body;

    private String captchaAnswer;

    private String captcha;

    /**
     * Constructs a {@code PublicMessageDto} with the given parameters. Trims all not-null fields.
     * 
     * @param authorName
     *            the anonymousMessage's author's name
     * @param body
     *            the anonymousMessage's body
     * @param captchaAnswer
     *            the CAPTCHA answer
     * @param captcha
     *            the correct CAPTCHA answer
     */
    public AnonymousMessageDto(String authorName, String body, String captchaAnswer, String captcha) {
        this.authorName = authorName == null ? null : authorName.trim();
        this.body = body == null ? null : body.trim();
        this.captchaAnswer = captchaAnswer == null ? null : captchaAnswer.trim();
        this.captcha = captcha == null ? null : captcha.trim();
    }

    /**
     * Creates a {@link AnonymousMessage}. Escapes HTML in the anonymous message's {@code body} and converts the
     * {@code body} 's text's line delimiters to HTML {@code <br>} tags.
     * 
     * @return a created {@link AnonymousMessage}
     * @see StringUtil#escapeHtmlInText(Set, String)
     * @see StringUtil#convertLineDelimitersToHtmlBR(String)
     */
    public AnonymousMessage createAnonymousMessage() {
        Set<HtmlTag> ignoredTags = new HashSet<HtmlTag>();
        ignoredTags.add(HtmlTag.A);
        ignoredTags.add(HtmlTag.B);
        ignoredTags.add(HtmlTag.BLOCKQUOTE);
        ignoredTags.add(HtmlTag.DEL);
        ignoredTags.add(HtmlTag.I);
        return new AnonymousMessage(StringUtil.convertLineDelimitersToHtmlBR(
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

    /**
     * @return the captchaAnswer
     */
    public String getCaptchaAnswer() {
        return captchaAnswer;
    }

    /**
     * @return the captcha
     */
    public String getCaptcha() {
        return captcha;
    }

}
