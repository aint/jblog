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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.aint.jblog.service.exception.other.UserRankConfigurationException;
import com.github.aint.jblog.service.exception.other.UserRankNotFoundException;
import com.github.aint.jblog.service.exception.other.XmlUserRankConfigurationException;
import com.github.aint.jblog.service.other.UserRankConfiguration;

/**
 * @author Olexandr Tyshkovets
 */
public class XmlUserRankConfigurationTest {
    private static final String USER_RANK_XML_PATH = "/com/github/aint/jblog/service/other/userRank-config.xml";
    private static final String USER_RANK_DTD_PATH = "/com/github/aint/jblog/service/other/userRank-config.dtd";
    private UserRankConfiguration userRankConfiguration;

    @BeforeClass
    public void beforeClass() throws XmlUserRankConfigurationException {
        userRankConfiguration = new XmlUserRankConfiguration(getClass().getResourceAsStream(USER_RANK_XML_PATH),
                getClass().getResourceAsStream(USER_RANK_DTD_PATH));
    }

    @Test(dataProvider = "userRatingData")
    public void getMaxRange(String rank, int[] expected) throws UserRankConfigurationException {
        assertEquals(userRankConfiguration.getMaxRange(rank), expected[1]);
    }

    @Test(dataProvider = "userRatingData")
    public void getMinRange(String rank, int[] expected) throws UserRankConfigurationException {
        assertEquals(userRankConfiguration.getMinRange(rank), expected[0]);
    }

    @Test(dataProvider = "userRankData")
    public void getRank(int rating, String expected) throws UserRankConfigurationException {
        assertEquals(userRankConfiguration.getRank(rating), expected);
    }

    @Test(expectedExceptions = UserRankNotFoundException.class)
    public void getRankNotFound() throws UserRankConfigurationException {
        userRankConfiguration.getRank(Integer.MIN_VALUE);
    }

    @DataProvider(name = "userRatingData")
    public Object[][] userRatingData() {
        return new Object[][] {
                new Object[] { "mega troll", new int[] { -1000, -100 } },
                new Object[] { "troll", new int[] { -99, -1 } },
                new Object[] { "junior", new int[] { 0, 25 } },
                new Object[] { "middle", new int[] { 26, 99 } },
                new Object[] { "senior", new int[] { 100, 1000 } },
        };
    }

    @DataProvider(name = "userRankData")
    public Object[][] userRankData() {
        return new Object[][] {
                new Object[] { -666, "mega troll" },
                new Object[] { -13, "troll" },
                new Object[] { 0, "junior" },
                new Object[] { 25, "junior" },
                new Object[] { 69, "middle" },
                new Object[] { 101, "senior" },
                new Object[] { 666, "senior" },
        };
    }

}
