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
package com.github.aint.jblog.service.validation.impl;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.aint.jblog.model.entity.AbstractArticle;
import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.model.entity.Language;
import com.github.aint.jblog.model.entity.PublicMessage;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.exception.validator.ValidationException;
import com.github.aint.jblog.service.validation.Validator;
import com.github.aint.jblog.service.validation.dto.ArticleDto;
import com.github.aint.jblog.service.validation.dto.PublicMessageDto;
import com.github.aint.jblog.service.validation.dto.RegisterUserDto;

/**
 * @author Olexandr Tyshkovets
 */
// TODO refactor this piece of shit
public class AnnotationBasedValidatorTest {
    private Validator validator;
    private List<Map<String, String>> userValidationErrorMsg;
    private List<Map<String, String>> articleValidationErrorMsg;
    private List<Map<String, String>> messageValidationErrorMsg;

    @BeforeClass
    public void beforeClass() {
        validator = new AnnotationBasedValidator();

        // UserDto
        userValidationErrorMsg = new ArrayList<Map<String, String>>();

        Map<String, String> map0 = new HashMap<String, String>();
        userValidationErrorMsg.add(map0);

        Map<String, String> u1 = new HashMap<String, String>();
        u1.put("userName", "user name can't be less than " + User.USERNAME_MIN_LENGTH);
        u1.put("day", "day is uncorrect");
        userValidationErrorMsg.add(u1);

        Map<String, String> u2 = new HashMap<String, String>();
        u2.put("email", "email is uncorrect");
        userValidationErrorMsg.add(u2);

        Map<String, String> u3 = new HashMap<String, String>();
        u3.put("password", "user password can't be less than " + User.PASSWORD_MIN_LENGTH);
        userValidationErrorMsg.add(u3);

        Map<String, String> u4 = new HashMap<String, String>();
        u4.put("userName", "user name can't be less than " + User.USERNAME_MIN_LENGTH);
        u4.put("email", "email is uncorrect");
        u4.put("password", "user password can't be less than " + User.PASSWORD_MIN_LENGTH);
        userValidationErrorMsg.add(u4);

        Map<String, String> u5 = new HashMap<String, String>();
        u5.put("rePassword", "re password must equals password");
        userValidationErrorMsg.add(u5);

        Map<String, String> u6 = new HashMap<String, String>();
        u6.put("day", "day is uncorrect");
        u6.put("month", "month is uncorrect");
        userValidationErrorMsg.add(u6);

        // ArticleDto
        articleValidationErrorMsg = new ArrayList<Map<String, String>>();

        Map<String, String> a0 = new HashMap<String, String>();
        a0.put("preview", "preview can't be less than " + Article.ARTICLE_PREVIEW_MIN_LENGTH);
        a0.put("body", "body can't be less than " + Article.ARTICLE_BODY_MIN_LENGTH);
        articleValidationErrorMsg.add(a0);

        Map<String, String> a1 = new HashMap<String, String>();
        a1.put("preview", "preview can't be less than " + Article.ARTICLE_PREVIEW_MIN_LENGTH);
        a1.put("body", "body can't be less than " + Article.ARTICLE_BODY_MIN_LENGTH);
        a1.put("keywords", "keywords can't be less than " + Article.ARTICLE_KEYWORDS_MIN_LENGTH);
        articleValidationErrorMsg.add(a1);

        Map<String, String> a2 = new HashMap<String, String>();
        a2.put("title", "title can't be less than " + AbstractArticle.TITLE_MIN_LENGTH);
        a2.put("preview", "preview can't be less than " + Article.ARTICLE_PREVIEW_MIN_LENGTH);
        a2.put("body", "body can't be less than " + Article.ARTICLE_BODY_MIN_LENGTH);
        a2.put("keywords", "keywords can't be less than " + Article.ARTICLE_KEYWORDS_MIN_LENGTH);
        articleValidationErrorMsg.add(a2);

        // MessageDto
        messageValidationErrorMsg = new ArrayList<Map<String, String>>();

        Map<String, String> m0 = new HashMap<String, String>();
        messageValidationErrorMsg.add(m0);

        Map<String, String> m1 = new HashMap<String, String>();
        m1.put("authorName", "author name can't be less than " + PublicMessage.AUTHOR_NAME_MIN_LENGTH);
        messageValidationErrorMsg.add(m1);

        Map<String, String> m2 = new HashMap<String, String>();
        m2.put("authorName", "author name can't be less than " + PublicMessage.AUTHOR_NAME_MIN_LENGTH);
        m2.put("body", "body can't be less than " + PublicMessage.PUBLIC_MESSAGE_BODY_MIN_LENGTH);
        messageValidationErrorMsg.add(m2);
    }

    @Test(dataProvider = "dtoData")
    public void validate(Object dto, Map<String, String> expected) throws ValidationException {
        assertEquals(validator.validate(dto), expected);
    }

    @DataProvider(name = "dtoData")
    public Object[][] dtoData() {
        return new Object[][] {
                // UserDto
                new Object[] { new RegisterUserDto("leo", "Lionel", "Messi", "messi@gmail.com", "111111", "111111",
                        "1", "1", "1987", Language.ENGLISH), userValidationErrorMsg.get(0) },
                new Object[] { new RegisterUserDto("ai", "Andreas", "Iniesta", "iniesta@gmail.com", "111111", "111111",
                        "abc", "1", "1987", Language.ENGLISH), userValidationErrorMsg.get(1) },
                new Object[] { new RegisterUserDto("franky", "Frank", "Lampard", "franky@gmail@mail.com", "111111",
                        "111111", "1", "1", "1987", Language.ENGLISH), userValidationErrorMsg.get(2) },
                new Object[] { new RegisterUserDto("wayne", "Wayne", "Rooney", "rooney@gmail.com", "1", "1", "1", "1",
                        "1987", Language.ENGLISH), userValidationErrorMsg.get(3) },
                new Object[] { new RegisterUserDto("cr", "Cristiana", "Ronaldo", "cr@gmail.com", "1", "1", "1", "1",
                        "1987", Language.ENGLISH), userValidationErrorMsg.get(4) },
                new Object[] { new RegisterUserDto("hugo", "Hugo", "Lloris", "lloris@gmail.com", "1111", "1234", "1",
                        "1", "1987", Language.ENGLISH), userValidationErrorMsg.get(5) },
                new Object[] { new RegisterUserDto("hugo", "Hugo", "Lloris", "lloris@gmail.com", "123456", "123456",
                        "0", "ab", "1985", Language.ENGLISH), userValidationErrorMsg.get(6) },
                // ArticleDto
                new Object[] { new ArticleDto("hello word", "This story discribes ...", "Let me tell you about ...",
                        "hello word, tutorial, first story"), articleValidationErrorMsg.get(0) },
                new Object[] { new ArticleDto("about java", "Java - is a programming language ...", "java, java ...",
                        " "), articleValidationErrorMsg.get(1) },
                new Object[] { new ArticleDto("java", "Java - is a programming language ...", "java, java ...", ""),
                        articleValidationErrorMsg.get(2) },
                // MessageDto
                new Object[] { new PublicMessageDto("robben", "Hello, my name is Arien."),
                        messageValidationErrorMsg.get(0) },
                new Object[] { new PublicMessageDto("", "xyz"), messageValidationErrorMsg.get(1) },
                new Object[] { new PublicMessageDto("", ""), messageValidationErrorMsg.get(2) },

        };
    }
}
