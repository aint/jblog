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
package com.github.aint.jblog.service.other.impl;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.aint.jblog.service.other.BrowserService;

/**
 * @author Olexandr Tyshkovets
 */
public class BrowserServiceImplTest {
    @Test(dataProvider = "browserFullVersionUserAgentData")
    public void getBrowserFullVersion(String userAgent, int[] expected) {
        final BrowserServiceImpl browserDef = new BrowserServiceImpl(userAgent);
        assertEquals(browserDef.getBrowserFullVersion(), expected);
    }

    @Test(dataProvider = "browserMajorVersionUserAgentData")
    public void getBrowserMajorVersion(String userAgent, int expected) {
        final BrowserService browserDef = new BrowserServiceImpl(userAgent);
        assertEquals(browserDef.getBrowserMajorVersion(), expected);
    }

    @Test(dataProvider = "browserNameUserAgentData")
    public void getBrowserName(String userAgent, String expected) {
        final BrowserService browserDef = new BrowserServiceImpl(userAgent);
        assertEquals(browserDef.getBrowserName(), expected);
    }

    @Test(dataProvider = "browserVersionUserAgentData")
    public void getBrowserVersion(String userAgent, String expected) {
        final BrowserServiceImpl browserDef = new BrowserServiceImpl(userAgent);
        assertEquals(browserDef.getBrowserVersion(), expected);
    }

    @DataProvider(name = "browserFullVersionUserAgentData")
    public Object[][] browserFullVersionUserAgentData() {
        return new Object[][] {
                // Google Chrome
                { "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1092.0 "
                        + "Safari/536.6", new int[] { 20, 0, 1092, 0 } },
                { "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.9 "
                        + "Safari/536.5", new int[] { 19, 0, 1084, 9 } },
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/536.3 (KHTML, like Gecko) "
                        + "Chrome/19.0.1063.0 Safari/536.3", new int[] { 19, 0, 1063, 0 } },
                { "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/18.6.872.0 "
                        + "Safari/535.2 UNTRUSTED/1.0 3gpp-gba UNTRUSTED/1.0", new int[] { 18, 6, 872, 0 } },
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.19 (KHTML, like Gecko) "
                        + "Chrome/18.0.1025.45 Safari/535.19", new int[] { 18, 0, 1025, 45 } },
                { "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 "
                        + "Safari/535.11", new int[] { 17, 0, 963, 66 } },
                { "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 "
                        + "Safari/535.11", new int[] { 17, 0, 963, 66 } },
                { "Mozilla/5.0 (Windows NT 6.0; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 "
                        + "Safari/535.11", new int[] { 17, 0, 963, 56 } },
                { "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.8 (KHTML, like Gecko) Chrome/16.0.912.63 Safari/535.8",
                        new int[] { 16, 0, 912, 63 } },
                { "Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.2 (KHTML, like Gecko) Ubuntu/11.10 " +
                        "Chromium/15.0.874.120 Chrome/15.0.874.120 Safari/535.2", new int[] { 15, 0, 874, 120 } },
                // Mozilla Firefox
                { "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:15.0) Gecko/20120427 Firefox/15.0a1", new int[] { 15, 0 } },
                { "Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20120403211507 Firefox/12.0", new int[] { 12, 0 } },
                { "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.1.16) Gecko/20120421 Firefox/11.0",
                        new int[] { 11, 0 } },
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:9.0) Gecko/20100101 Firefox/9.0.1",
                        new int[] { 9, 0, 1 } },
                { "Mozilla/5.0 (Windows NT 6.1; rv:6.0) Gecko/20110814 Firefox/6.0", new int[] { 6, 0 } },
                { "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:5.0) Gecko/20110619 Firefox/5.0", new int[] { 5, 0 } },
                { "Mozilla/5.0 (X11; U; Linux i686; en-GB; rv:2.0) Gecko/20110404 Fedora/16-dev Firefox/4.0b12pre",
                        new int[] { 4, 0 } },
                { "Mozilla/5.0 (X11; U; Linux i686; de; rv:1.9.2.3) Gecko/20100423 Ubuntu/10.04 (lucid) Firefox/3.6.3",
                        new int[] { 3, 6, 3 } },
                { "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.8) Gecko/20100806 Firefox/3.6",
                        new int[] { 3, 6 } },
                // Microsoft Internet Explorer
                { "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)", new int[] { 10, 0 } },
                { "Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)", new int[] { 9, 0 } },
                { "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET "
                        + "CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; Tablet PC 2.0; .NET4.0C; .NET4.0E)",
                        new int[] { 8, 0 } },
                { "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; SLCC2; .NET CLR 2.0.50727; InfoPath.3; "
                        + ".NET4.0C; .NET4.0E; .NET CLR 3.5.30729; .NET CLR 3.0.30729; MS-RTC LM 8)",
                        new int[] { 7, 0 } },
                { "Mozilla/4.0 (Compatible; Windows NT 5.1; MSIE 6.0) (compatible; MSIE 6.0; Windows NT 5.1; .NET "
                        + "CLR 1.1.4322; .NET CLR 2.0.50727)", new int[] { 6, 0 } },
                // Opera
                { "Opera/9.80 (Windows NT 6.1; U; es-ES) Presto/2.9.181 Version/12.00", new int[] { 12, 0 } },
                { "Opera/9.80 (Windows NT 5.1; U; en) Presto/2.9.168 Version/11.51", new int[] { 11, 51 } },
                { "Opera/9.80 (X11; Linux x86_64; U; Ubuntu/10.10 (maverick); pl) Presto/2.7.62 Version/11.01",
                        new int[] { 11, 01 } },
                { "Opera/9.80 (Windows NT 6.1; U; ru) Presto/2.7.62 Version/11.01", new int[] { 11, 01 } },
                { "Opera/9.80 (Windows NT 6.1; U; pl) Presto/2.7.62 Version/11.00", new int[] { 11, 0 } },
                { "Opera/9.80 (Windows NT 6.0; U; zh-cn) Presto/2.5.22 Version/10.50", new int[] { 10, 50 } },
                { "Opera/9.80 (X11; Linux i686; U; pt-BR) Presto/2.2.15 Version/10.00", new int[] { 10, 0 } },
                { "Opera/9.51 (Windows NT 5.1; U; fr)", new int[] { 9, 51 } },
                { "Opera/9.00 (Windows NT 5.1; U; ru)", new int[] { 9, 0 } },
                // Apple Safari
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/534.55.3 (KHTML, like Gecko) "
                        + "Version/5.1.3 Safari/534.53.10", new int[] { 5, 1, 3 } },
                { "Mozilla/5.0 (iPad; CPU OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko ) Version/5.1 "
                        + "Mobile/9B176 Safari/7534.48.3", new int[] { 5, 1 } },
                { "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; ja-jp) AppleWebKit/533.20.25 (KHTML, like "
                        + "Gecko) Version/5.0.4 Safari/533.20.27", new int[] { 5, 0, 4 } },
                { "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.19.4 (KHTML, like Gecko) "
                        + "Version/5.0.2 Safari/533.18.5", new int[] { 5, 0, 2 } },
                { "Mozilla/5.0 (X11; U; Linux x86_64; en-us) AppleWebKit/531.2+ (KHTML, like Gecko) Version/5.0 "
                        + "Safari/531.2+", new int[] { 5, 0 } },
                { "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_7; en-us) AppleWebKit/533.4 (KHTML, like Gecko) "
                        + "Version/4.1 Safari/533.4", new int[] { 4, 1 } },
                { "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3; en-us) AppleWebKit/531.21.11 (KHTML, like "
                        + "Gecko) Version/4.0.4 Safari/531.21.10", new int[] { 4, 0, 4 } },
                { "Mozilla/5.0 (Windows; U; Windows NT 6.0; ja-JP) AppleWebKit/528.16 (KHTML, like Gecko) "
                        + "Version/4.0 Safari/528.16", new int[] { 4, 0 } },
                { "Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_4_11; pl-pl) AppleWebKit/525.27.1 (KHTML, like Gecko) "
                        + "Version/3.2.1 Safari/525.27.1", new int[] { 3, 2, 1 } },

                { "abcdefg", new int[] {} },
                { "", new int[] {} },
        };
    }

    @DataProvider(name = "browserMajorVersionUserAgentData")
    public Object[][] browserMajorVersionUserAgentData() {
        return new Object[][] {
                // Google Chrome
                { "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1092.0 "
                        + "Safari/536.6", 20 },
                { "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.9 "
                        + "Safari/536.5", 19 },
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/536.3 (KHTML, like Gecko) "
                        + "Chrome/19.0.1063.0 Safari/536.3", 19 },
                { "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/18.6.872.0 Safari/535.2 "
                        + "UNTRUSTED/1.0 3gpp-gba UNTRUSTED/1.0", 18 },
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.19 (KHTML, like Gecko) "
                        + "Chrome/18.0.1025.45 Safari/535.19", 18 },
                { "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 "
                        + "Safari/535.11", 17 },
                { "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 "
                        + "Safari/535.11", 17 },
                { "Mozilla/5.0 (Windows NT 6.0; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 "
                        + "Safari/535.11", 17 },
                { "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.8 (KHTML, like Gecko) Chrome/16.0.912.63 Safari/535.8",
                        16 },
                { "Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.2 (KHTML, like Gecko) Ubuntu/11.10 "
                        + "Chromium/15.0.874.120 Chrome/15.0.874.120 Safari/535.2", 15 },
                // Mozilla Firefox
                { "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:15.0) Gecko/20120427 Firefox/15.0a1", 15 },
                { "Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20120403211507 Firefox/12.0", 12 },
                { "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.1.16) Gecko/20120421 Firefox/11.0", 11 },
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:9.0) Gecko/20100101 Firefox/9.0", 9 },
                { "Mozilla/5.0 (Windows NT 6.1; rv:6.0) Gecko/20110814 Firefox/6.0", 6 },
                { "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:5.0) Gecko/20110619 Firefox/5.0", 5 },
                { "Mozilla/5.0 (X11; U; Linux i686; en-GB; rv:2.0) Gecko/20110404 Fedora/16-dev Firefox/4.0", 4 },
                { "Mozilla/5.0 (X11; U; Linux i686; de; rv:1.9.2.3) Gecko/20100423 Ubuntu/10.04 (lucid) Firefox/3.6.3",
                        3 },
                { "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.8) Gecko/20100806 Firefox/3.6", 3 },
                // Microsoft Internet Explorer
                { "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)", 10 },
                { "Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)", 9 },
                { "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET "
                        + "CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; Tablet PC 2.0; .NET4.0C; .NET4.0E)",
                        8 },
                { "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; SLCC2; .NET CLR 2.0.50727; InfoPath.3; "
                        + ".NET4.0C; .NET4.0E; .NET CLR 3.5.30729; .NET CLR 3.0.30729; MS-RTC LM 8)", 7 },
                { "Mozilla/4.0 (Compatible; Windows NT 5.1; MSIE 6.0) (compatible; MSIE 6.0; Windows NT 5.1; .NET "
                        + "CLR 1.1.4322; .NET CLR 2.0.50727)", 6 },
                // Opera
                { "Opera/9.80 (Windows NT 6.1; U; es-ES) Presto/2.9.181 Version/12.00", 12 },
                { "Opera/9.80 (Windows NT 5.1; U; en) Presto/2.9.168 Version/11.51", 11 },
                { "Opera/9.80 (X11; Linux x86_64; U; Ubuntu/10.10 (maverick); pl) Presto/2.7.62 Version/11.01", 11 },
                { "Opera/9.80 (Windows NT 6.1; U; ru) Presto/2.7.62 Version/11.01", 11 },
                { "Opera/9.80 (Windows NT 6.1; U; pl) Presto/2.7.62 Version/11.00", 11 },
                { "Opera/9.80 (Windows NT 6.0; U; zh-cn) Presto/2.5.22 Version/10.50", 10 },
                { "Opera/9.80 (X11; Linux i686; U; pt-BR) Presto/2.2.15 Version/10.00", 10 },
                { "Opera/9.51 (Windows NT 5.1; U; fr)", 9 },
                { "Opera/9.00 (Windows NT 5.1; U; ru)", 9 },
                // Apple Safari
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/534.55.3 (KHTML, like Gecko) "
                        + "Version/5.1.3 Safari/534.53.10", 5 },
                { "Mozilla/5.0 (iPad; CPU OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko ) Version/5.1 "
                        + "Mobile/9B176 Safari/7534.48.3", 5 },
                { "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; ja-jp) AppleWebKit/533.20.25 (KHTML, like "
                        + "Gecko) Version/5.0.4 Safari/533.20.27", 5 },
                { "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.19.4 (KHTML, like Gecko) "
                        + "Version/5.0.2 Safari/533.18.5", 5 },
                { "Mozilla/5.0 (X11; U; Linux x86_64; en-us) AppleWebKit/531.2+ (KHTML, like Gecko) Version/5.0 "
                        + "Safari/531.2+", 5 },
                { "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_7; en-us) AppleWebKit/533.4 (KHTML, like Gecko) "
                        + "Version/4.1 Safari/533.4", 4 },
                { "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3; en-us) AppleWebKit/531.21.11 (KHTML, like "
                        + "Gecko) Version/4.0.4 Safari/531.21.10", 4 },
                { "Mozilla/5.0 (Windows; U; Windows NT 6.0; ja-JP) AppleWebKit/528.16 (KHTML, like Gecko) "
                        + "Version/4.0 Safari/528.16", 4 },
                { "Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_4_11; pl-pl) AppleWebKit/525.27.1 (KHTML, like Gecko) "
                        + "Version/3.2.1 Safari/525.27.1", 3 },

                { "abcdefg", -1 },
                { "", -1 },
        };
    }

    @DataProvider(name = "browserNameUserAgentData")
    public Object[][] browserNameUserAgentData() {
        return new Object[][] {
                // Google Chrome
                { "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1092.0 "
                        + "Safari/536.6", "Chrome" },
                { "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.9 "
                        + "Safari/536.5", "Chrome" },
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/536.3 (KHTML, like Gecko) "
                        + "Chrome/19.0.1063.0 Safari/536.3", "Chrome" },
                { "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/18.6.872.0 Safari/535.2 "
                        + "UNTRUSTED/1.0 3gpp-gba UNTRUSTED/1.0", "Chrome" },
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.19 (KHTML, like Gecko) "
                        + "Chrome/18.0.1025.45 Safari/535.19", "Chrome" },
                { "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 "
                        + "Safari/535.11", "Chrome" },
                { "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 "
                        + "Safari/535.11", "Chrome" },
                { "Mozilla/5.0 (Windows NT 6.0; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 "
                        + "Safari/535.11", "Chrome" },
                { "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.8 (KHTML, like Gecko) Chrome/16.0.912.63 Safari/535.8",
                        "Chrome" },
                { "Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.2 (KHTML, like Gecko) Ubuntu/11.10 "
                        + "Chromium/15.0.874.120 Chrome/15.0.874.120 Safari/535.2", "Chrome" },
                // Mozilla Firefox
                { "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:15.0) Gecko/20120427 Firefox/15.0a1", "Firefox" },
                { "Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20120403211507 Firefox/12.0", "Firefox" },
                { "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.1.16) Gecko/20120421 Firefox/11.0", "Firefox" },
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:9.0) Gecko/20100101 Firefox/9.0", "Firefox" },
                { "Mozilla/5.0 (Windows NT 6.1; rv:6.0) Gecko/20110814 Firefox/6.0", "Firefox" },
                { "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:5.0) Gecko/20110619 Firefox/5.0", "Firefox" },
                { "Mozilla/5.0 (X11; U; Linux i686; en-GB; rv:2.0) Gecko/20110404 Fedora/16-dev Firefox/4.0",
                        "Firefox" },
                { "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8",
                        "Firefox" },
                { "Mozilla/5.0 (X11; U; Linux i686; de; rv:1.9.2.3) Gecko/20100423 Ubuntu/10.04 (lucid) Firefox/3.6.3",
                        "Firefox" },
                { "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.8) Gecko/20100806 Firefox/3.6", "Firefox" },
                // Microsoft Internet Explorer
                { "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)", "MSIE" },
                { "Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)", "MSIE" },
                { "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET "
                        + "CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; Tablet PC 2.0; .NET4.0C; .NET4.0E)",
                        "MSIE" },
                { "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; SLCC2; .NET CLR 2.0.50727; InfoPath.3; "
                        + ".NET4.0C; .NET4.0E; .NET CLR 3.5.30729; .NET CLR 3.0.30729; MS-RTC LM 8)", "MSIE" },
                { "Mozilla/4.0 (Compatible; Windows NT 5.1; MSIE 6.0) (compatible; MSIE 6.0; Windows NT 5.1; .NET "
                        + "CLR 1.1.4322; .NET CLR 2.0.50727)", "MSIE" },
                // Opera
                { "Opera/9.80 (Windows NT 6.1; U; es-ES) Presto/2.9.181 Version/12.00", "Opera" },
                { "Opera/9.80 (Windows NT 5.1; U; en) Presto/2.9.168 Version/11.51", "Opera" },
                { "Opera/9.80 (X11; Linux x86_64; U; Ubuntu/10.10 (maverick); pl) Presto/2.7.62 Version/11.01",
                        "Opera" },
                { "Opera/9.80 (Windows NT 6.1; U; ru) Presto/2.7.62 Version/11.01", "Opera" },
                { "Opera/9.80 (Windows NT 6.1; U; pl) Presto/2.7.62 Version/11.00", "Opera" },
                { "Opera/9.80 (Windows NT 6.0; U; zh-cn) Presto/2.5.22 Version/10.50", "Opera" },
                { "Opera/9.80 (X11; Linux i686; U; pt-BR) Presto/2.2.15 Version/10.00", "Opera" },
                { "Opera/9.51 (Windows NT 5.1; U; fr)", "Opera" },
                { "Opera/9.00 (Windows NT 5.1; U; ru)", "Opera" },
                // Apple Safari
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/534.55.3 (KHTML, like Gecko) "
                        + "Version/5.1.3 Safari/534.53.10", "Safari" },
                { "Mozilla/5.0 (iPad; CPU OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko ) Version/5.1 "
                        + "Mobile/9B176 Safari/7534.48.3", "Safari" },
                { "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; ja-jp) AppleWebKit/533.20.25 (KHTML, like "
                        + "Gecko) Version/5.0.4 Safari/533.20.27", "Safari" },
                { "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.19.4 (KHTML, like Gecko) "
                        + "Version/5.0.2 Safari/533.18.5", "Safari" },
                { "Mozilla/5.0 (X11; U; Linux x86_64; en-us) AppleWebKit/531.2+ (KHTML, like Gecko) Version/5.0 "
                        + "Safari/531.2+", "Safari" },
                { "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_7; en-us) AppleWebKit/533.4 (KHTML, like Gecko) "
                        + "Version/4.1 Safari/533.4", "Safari" },
                { "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3; en-us) AppleWebKit/531.21.11 (KHTML, like "
                        + "Gecko) Version/4.0.4 Safari/531.21.10", "Safari" },
                { "Mozilla/5.0 (Windows; U; Windows NT 6.0; ja-JP) AppleWebKit/528.16 (KHTML, like Gecko) "
                        + "Version/4.0 Safari/528.16", "Safari" },
                { "Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_4_11; pl-pl) AppleWebKit/525.27.1 (KHTML, like Gecko) "
                        + "Version/3.2.1 Safari/525.27.1", "Safari" },

                { "abcdefg", "" },
                { "", "" },
        };
    }

    @DataProvider(name = "browserVersionUserAgentData")
    public Object[][] browserVersionUserAgentData() {
        return new Object[][] {
                // Google Chrome
                { "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1092.0 "
                        + "Safari/536.6", "20.0.1092.0" },
                { "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.9 "
                        + "Safari/536.5", "19.0.1084.9" },
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/536.3 (KHTML, like Gecko) "
                        + "Chrome/19.0.1063.0 Safari/536.3", "19.0.1063.0" },
                { "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/18.6.872.0 Safari/535.2 "
                        + "UNTRUSTED/1.0 3gpp-gba UNTRUSTED/1.0", "18.6.872.0" },
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.19 (KHTML, like Gecko) "
                        + "Chrome/18.0.1025.45 Safari/535.19", "18.0.1025.45" },
                { "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 "
                        + "Safari/535.11", "17.0.963.66" },
                { "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.66 "
                        + "Safari/535.11", "17.0.963.66" },
                { "Mozilla/5.0 (Windows NT 6.0; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 "
                        + "Safari/535.11", "17.0.963.56" },
                { "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.8 (KHTML, like Gecko) Chrome/16.0.912.63 Safari/535.8",
                        "16.0.912.63" },
                { "Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.2 (KHTML, like Gecko) Ubuntu/11.10 "
                        + "Chromium/15.0.874.120 Chrome/15.0.874.120 Safari/535.2", "15.0.874.120" },
                // Mozilla Firefox
                { "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:15.0) Gecko/20120427 Firefox/15.0a1", "15.0" },
                { "Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20120403211507 Firefox/12.0", "12.0" },
                { "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.1.16) Gecko/20120421 Firefox/11.0", "11.0" },
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:9.0) Gecko/20100101 Firefox/9.0", "9.0" },
                { "Mozilla/5.0 (Windows NT 6.1; rv:6.0) Gecko/20110814 Firefox/6.0", "6.0" },
                { "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:5.0) Gecko/20110619 Firefox/5.0", "5.0" },
                { "Mozilla/5.0 (X11; U; Linux i686; en-GB; rv:2.0) Gecko/20110404 Fedora/16-dev Firefox/4.0", "4.0" },
                { "Mozilla/5.0 (X11; U; Linux i686; de; rv:1.9.2.3) Gecko/20100423 Ubuntu/10.04 (lucid) Firefox/3.6.3",
                        "3.6.3" },
                { "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.8) Gecko/20100806 Firefox/3.6", "3.6" },
                // Microsoft Internet Explorer
                { "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)", "10.0" },
                { "Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)", "9.0" },
                { "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; "
                        + ".NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; Tablet PC 2.0; .NET4.0C; "
                        + ".NET4.0E)", "8.0" },
                { "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; SLCC2; .NET CLR 2.0.50727; InfoPath.3; "
                        + ".NET4.0C; .NET4.0E; .NET CLR 3.5.30729; .NET CLR 3.0.30729; MS-RTC LM 8)", "7.0" },
                { "Mozilla/4.0 (Compatible; Windows NT 5.1; MSIE 6.0) (compatible; MSIE 6.0; Windows NT 5.1; .NET "
                        + "CLR 1.1.4322; .NET CLR 2.0.50727)", "6.0" },
                // Opera
                { "Opera/9.80 (Windows NT 6.1; U; es-ES) Presto/2.9.181 Version/12.00", "12.00" },
                { "Opera/9.80 (Windows NT 5.1; U; en) Presto/2.9.168 Version/11.51", "11.51" },
                { "Opera/9.80 (X11; Linux x86_64; U; Ubuntu/10.10 (maverick); pl) Presto/2.7.62 Version/11.01",
                        "11.01" },
                { "Opera/9.80 (Windows NT 6.1; U; ru) Presto/2.7.62 Version/11.01", "11.01" },
                { "Opera/9.80 (Windows NT 6.1; U; pl) Presto/2.7.62 Version/11.00", "11.00" },
                { "Opera/9.80 (Windows NT 6.0; U; zh-cn) Presto/2.5.22 Version/10.50", "10.50" },
                { "Opera/9.80 (X11; Linux i686; U; pt-BR) Presto/2.2.15 Version/10.00", "10.00" },
                { "Opera/9.51 (Windows NT 5.1; U; fr)", "9.51" },
                { "Opera/9.00 (Windows NT 5.1; U; ru)", "9.00" },
                // Apple Safari
                { "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/534.55.3 (KHTML, like Gecko) "
                        + "Version/5.1.3 Safari/534.53.10", "5.1.3" },
                { "Mozilla/5.0 (iPad; CPU OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko ) "
                        + "Version/5.1 Mobile/9B176 Safari/7534.48.3", "5.1" },
                { "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; ja-jp) AppleWebKit/533.20.25 (KHTML, "
                        + "like Gecko) Version/5.0.4 Safari/533.20.27", "5.0.4" },
                { "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.19.4 (KHTML, like Gecko) "
                        + "Version/5.0.2 Safari/533.18.5", "5.0.2" },
                { "Mozilla/5.0 (X11; U; Linux x86_64; en-us) AppleWebKit/531.2+ (KHTML, like Gecko) Version/5.0 "
                        + "Safari/531.2+", "5.0" },
                { "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_7; en-us) AppleWebKit/533.4 (KHTML, like Gecko) "
                        + "Version/4.1 Safari/533.4", "4.1" },
                { "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3; en-us) AppleWebKit/531.21.11 (KHTML, like "
                        + "Gecko) Version/4.0.4 Safari/531.21.10", "4.0.4" },
                { "Mozilla/5.0 (Windows; U; Windows NT 6.0; ja-JP) AppleWebKit/528.16 (KHTML, like Gecko) "
                        + "Version/4.0 Safari/528.16", "4.0" },
                { "Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_4_11; pl-pl) AppleWebKit/525.27.1 (KHTML, like Gecko) "
                        + "Version/3.2.1 Safari/525.27.1", "3.2.1" },

                { "abcdefg", "" },
                { "", "" },
        };
    }

}
