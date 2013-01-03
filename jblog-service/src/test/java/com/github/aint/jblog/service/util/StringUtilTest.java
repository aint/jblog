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

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Olexandr Tyshkovets
 */
public class StringUtilTest {
    @Test(dataProvider = "textWithHtmlBRLineDelimiters")
    public void convertHtmlBRLineDelimitersToLF(String text, String expected) {
        assertEquals(StringUtil.convertHtmlBRLineDelimitersToLF(text), expected);
    }

    @Test(dataProvider = "textWithLFLineDelimiters")
    public void convertLineDelimitersToHtmlBR(String text, String expected) {
        assertEquals(StringUtil.convertLineDelimitersToHtmlBR(text), expected);
    }

    @DataProvider(name = "textWithHtmlBRLineDelimiters")
    public Object[][] textWithHtmlBRLineDelimiters() {
        return new Object[][] {
                { "Haters call me bitch<br><br>"
                        + "Call me faggot<br><br>"
                        + "Call me whitey.<br><br>"
                        + "But I'm something you can never be.<br><br>",
                        "Haters call me bitch\n"
                                + "Call me faggot\n"
                                + "Call me whitey.\n"
                                + "But I'm something you can never be.\n" },
                { "I won't look prettier<br><br>"
                        + "If I smile for the picture.<br><br>"
                        + "Motherfuckers never liked me then,<br><br>"
                        + "And they sure won't like me now.<br><br>",
                        "I won't look prettier\n"
                                + "If I smile for the picture.\n"
                                + "Motherfuckers never liked me then,\n"
                                + "And they sure won't like me now.\n" },
        };
    }

    @DataProvider(name = "textWithLFLineDelimiters")
    public Object[][] textWithLFLineDelimiters() {
        return new Object[][] {
                { "Haters call me bitch\n"
                        + "Call me faggot\n"
                        + "Call me whitey.\n"
                        + "But I'm something you can never be.\n",
                        "Haters call me bitch<br><br>"
                                + "Call me faggot<br><br>"
                                + "Call me whitey.<br><br>"
                                + "But I'm something you can never be.<br><br>" },
                { "I won't look prettier\n"
                        + "If I smile for the picture.\n"
                        + "Motherfuckers never liked me then,\n"
                        + "And they sure won't like me now.\n",
                        "I won't look prettier<br><br>"
                                + "If I smile for the picture.<br><br>"
                                + "Motherfuckers never liked me then,<br><br>"
                                + "And they sure won't like me now.<br><br>" },
        };
    }

}
