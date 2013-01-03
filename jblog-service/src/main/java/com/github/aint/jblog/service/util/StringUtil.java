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
package com.github.aint.jblog.service.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides static methods for manipulating String.
 * 
 * @author Olexandr Tyshkovets
 */
public class StringUtil {
    /**
     * * Generates new salt string, like "dd238b74".
     * 
     * @return a salt string
     */
    public static String generateSalt() {
        Pattern pattern = Pattern.compile("([a-z\\d]*)-([a-z\\d]*)-([a-z\\d]*)-([a-z\\d]*)-([a-z\\d]*)");
        Matcher matcher = pattern.matcher(UUID.randomUUID().toString());
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return "abc123";
    }

    /**
     * Converts the {@code text}'s line delimiters to HTML {@code <br>} tags, e.g. input:
     * {@code 'Text text.\nText text ...'}, output: {@code 'Text text.<br>
     * <br>
     * Text text ...<br>
     * <br>
     * '}.
     * 
     * @param text
     *            the text whose line delimiters will be converted
     * @return a text whose line delimiters were converted to HTML {@code <br>} tags
     * @throws IllegalArgumentException
     *             if {@code text} is {@code null}
     * @see StringUtil#convertHtmlBRLineDelimitersToLF(String)
     */
    public static String convertLineDelimitersToHtmlBR(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text can't be null");
        }

        return text.replace("\n", "<br><br>");
    }

    /**
     * Converts the {@code text}'s HTML line delimiters ({@code <br>} tags) to true {@code LF}, e.g. input: {@code 'Text text.
     * <br>
     * <br>
     * Text text ...<br>
     * <br>
     * '}, output: {@code 'Text text.\nText text ...\n'}.
     * 
     * @param text
     *            the text whose HTML line delimiters ({@code <br>} tags) will be converted
     * @return a text whose HTML line delimiters ({@code <br>} tags) were converted to true {@code LF}
     * @throws IllegalArgumentException
     *             if {@code text} is {@code null}
     * @see StringUtil#convertLineDelimitersToHtmlBR(String)
     */
    public static String convertHtmlBRLineDelimitersToLF(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text can't be null");
        }

        return text.replace("<br><br>", "\n");
    }

    /**
     * Escapes all HTML tags without {@code ignoredTags} in the given {@code text}, e.g. input(&lth1&gt - will be
     * ignored): {@code <html><body><h1>hello word</h1></body></html>}, output: {@code &lthtml&gt&ltbody&gt
     * <h1>hello word</h1>&lt/body&gt&lt/html&gt}.
     * 
     * @param ignoreTags
     *            this HTML tags won't be escaped
     * @param text
     *            the text which contains HTML tags
     * @return a text with escaped HTML tags
     * @throws IllegalArgumentException
     *             if {@code ignoredTags} or {@code text} is {@code null}
     * @see HtmlTag
     */
    public static String escapeHtmlInText(Set<HtmlTag> ignoreTags, String text) {
        if (ignoreTags == null) {
            throw new IllegalArgumentException("ignoreTags can't be null");
        }
        if (text == null) {
            throw new IllegalArgumentException("text can't be null");
        }

        StringBuilder sb = null;
        for (HtmlTag tag : ignoreTags) {
            sb = new StringBuilder(text.length());
            int startIndex = 0;
            Matcher matcher = tag.getPattern().matcher(text);
            while (matcher.find()) {
                sb.append(text.substring(startIndex, matcher.start()));
                startIndex = matcher.end();

                sb.append(text.substring(matcher.start(), matcher.end()).replaceAll("&", "#amp").replaceAll("<", "#lt")
                        .replaceAll(">", "#gt").replaceAll("\"", "#quot"));
            }
            sb.append(text.substring(startIndex, text.length()));
            text = sb.toString();
        }

        return text.replaceAll("&", "&amp").replaceAll("<", "&lt").replaceAll(">", "&gt").replaceAll("\"", "&quot")
                .replaceAll("#amp", "&").replaceAll("#lt", "<").replaceAll("#gt", ">").replaceAll("#quot", "\"");
    }

    /**
     * Processes the given {@code keywords} to save in correct representation, e.g. before processing:
     * " , first,second,,,", after processing: "first, second".
     * 
     * @param keywords
     *            the keywords which will be processed
     * @return keywords in correct representation
     * @throws IllegalArgumentException
     *             if {@code keywords} is {@code null} or its length equals zero
     */
    public static String processKeywords(String keywords) {
        if (keywords == null) {
            throw new IllegalArgumentException("keywords can't be null");
        }
        if (keywords.length() == 0) {
            throw new IllegalArgumentException("keywords' length can't be zero");
        }

        List<String> wordList = new ArrayList<String>();
        Collections.addAll(wordList, keywords.split(","));
        StringBuilder words = new StringBuilder();
        Set<String> wordSet = new HashSet<String>();
        for (String w : wordList) {
            String word = w.trim();
            if (word.length() != 0 && !wordSet.contains(word)) {
                wordSet.add(word);
                words.append(word);
                words.append(", ");
            }
        }

        return words.substring(0, words.length() - 2).toString(); // because last two chars == ", "
    }

}
