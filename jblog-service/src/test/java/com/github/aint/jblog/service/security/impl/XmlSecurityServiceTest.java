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
package com.github.aint.jblog.service.security.impl;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.aint.jblog.model.entity.Role;
import com.github.aint.jblog.service.exception.security.XmlSecurityException;

/**
 * @author Olexandr Tyshkovets
 */
public class XmlSecurityServiceTest {
    private static final String SECURITY_CONFIG_XML_PATH = "/com/github/aint/jblog/service/security/"
            + "security-config.xml";
    private static final String SECURITY_CONFIG_DTD_PATH = "/com/github/aint/jblog/service/security/"
            + "security-config.dtd";
    private XmlSecurityService securityService;

    @BeforeClass
    public void beforeClass() throws XmlSecurityException {
        securityService = new XmlSecurityService(getClass().getResourceAsStream(SECURITY_CONFIG_XML_PATH),
                getClass().getResourceAsStream(SECURITY_CONFIG_DTD_PATH));
    }

    @Test(dataProvider = "pathData")
    public void getPathsByRole(Role role, Collection<String> expected) {
        assertEquals(securityService.getPathsByRole(role), new HashSet<String>(expected));
    }

    @Test(dataProvider = "roleData")
    public void getRolesByPath(String uri, Collection<Role> expected) {
        assertEquals(securityService.getRolesByPath(uri), new HashSet<Role>(expected));
    }

    @Test(dataProvider = "authorizeUriData")
    public void isAuthorize(Object[] data, boolean expected) {
        assertEquals(securityService.isAuthorize((String) data[0], (Role) data[1]), expected);
    }

    @Test(dataProvider = "processUriData")
    public void processUri(String uri, String expected) {
        assertEquals(securityService.processUri(uri), expected);
    }

    @DataProvider(name = "pathData")
    public Object[][] pathData() {
        return new Object[][] {
                new Object[] { Role.GUEST, Arrays.asList("/index", "other-actions") },
                new Object[] { Role.USER, Arrays.asList("/index", "/users", "/add-comment", "/vote", "other-actions") },
                new Object[] { Role.ADMIN, Arrays.asList("/index", "/users", "/add-comment", "/vote", "/only-admin",
                        "other-actions") },
        };
    }

    @DataProvider(name = "roleData")
    public Object[][] roleData() {
        return new Object[][] {
                new Object[] { "/add-comment", Arrays.asList(Role.USER, Role.ADMIN) },
                new Object[] { "/index", Arrays.asList(Role.GUEST, Role.USER, Role.ADMIN) },
                new Object[] { "/only-admin", Arrays.asList(Role.ADMIN) },
                new Object[] { "/some-uri", Arrays.asList() },
        };
    }

    @DataProvider(name = "authorizeUriData")
    public Object[][] authorizeUriData() {
        return new Object[][] {
                new Object[] { new Object[] { "/index", Role.GUEST }, true },
                new Object[] { new Object[] { "/users", Role.GUEST }, false },
                new Object[] { new Object[] { "/users", Role.ADMIN }, true },
                new Object[] { new Object[] { "/add-comment", Role.USER }, true },
                new Object[] { new Object[] { "/vote", Role.GUEST }, false },
                new Object[] { new Object[] { "/some-uri", Role.GUEST }, true },
        };
    }

    @DataProvider(name = "processUriData")
    public Object[][] processUriData() {
        return new Object[][] {
                new Object[] { "index", "index" },
                new Object[] { "/index", "/index" },
                new Object[] { "/index/", "/index" },
                new Object[] { "/display-article/1", "/display-article" },
                new Object[] { "/display-article/21", "/display-article" },
                new Object[] { "/display-article/-6", "/display-article" },
                new Object[] { "/display-article/xyz", "/display-article" },
                new Object[] { "/display-article//1", "/display-article//1" },
                new Object[] { "/ar/ti/cl/es", "/ar/ti/cl/es" },
                new Object[] { "/ar-ti-cl-es", "/ar-ti-cl-es" },

                new Object[] { "/", "/" },
                new Object[] { "n/", "n/" },
                new Object[] { "/1", "/1" },
                new Object[] { "", "" },
        };
    }

}
