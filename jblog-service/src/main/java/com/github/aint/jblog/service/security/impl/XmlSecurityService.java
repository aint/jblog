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

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.github.aint.jblog.model.entity.Role;
import com.github.aint.jblog.service.exception.security.XmlSecurityException;
import com.github.aint.jblog.service.security.SecurityService;

/**
 * This class is a XML based implementation of the {@code SecurityException} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see SecurityService
 */
public class XmlSecurityService implements SecurityService {
    private static final Pattern uriPattern = Pattern.compile("(/[a-z-]+)/([\\d-a-z]*)");
    private static final String OTHER_ACTIONS_XML_ATTRIBUTE = "other-actions";
    private static final String PATH_XML_ATTRIBUTE = "path";
    private static final String NAME_XML_ATTRIBUTE = "name";
    private final Map<String, Set<Role>> authorizationMap = new HashMap<String, Set<Role>>();

    /**
     * Constructs a {@code XmlSecurityService} and reads data from the specified input stream.
     * 
     * @param xml
     *            an {@code InputStream} of a XML file which contains authorization data
     * @throws IllegalArgumentException
     *             if {@code xml} is {@code null}
     * @throws XmlSecurityException
     *             if an error has occurred while performing parsing
     */
    public XmlSecurityService(InputStream xml) throws XmlSecurityException {
        if (xml == null) {
            throw new IllegalArgumentException("xml can't be null");
        }
        try {
            init(xml, null);
        } catch (Exception e) {
            throw new XmlSecurityException(e);
        }
    }

    /**
     * Constructs a {@code XmlSecurityService} and reads data from the specified input streams.
     * 
     * @param xml
     *            an {@code InputStream} of a XML file which contains authorization data
     * @param dtd
     *            an {@code InputStream} of {@code xml}'s a DTD file
     * @throws IllegalArgumentException
     *             if {@code xml} is {@code null}
     * @throws IllegalArgumentException
     *             if {@code dtd} is {@code null}
     * @throws XmlSecurityException
     *             if an error has occurred while performing parsing
     */
    public XmlSecurityService(InputStream xml, InputStream dtd) throws XmlSecurityException {
        if (xml == null) {
            throw new IllegalArgumentException("xml can't be null");
        }
        if (dtd == null) {
            throw new IllegalArgumentException("dtd can't be null");
        }
        try {
            init(xml, dtd);
        } catch (Exception e) {
            throw new XmlSecurityException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAuthorize(String path, Role role) {
        if (path == null) {
            throw new IllegalArgumentException("path can't be null");
        }
        if (role == null) {
            throw new IllegalArgumentException("role can't be null");
        }

        Set<Role> roleSet = authorizationMap.get(processUri(path));
        if (roleSet == null) {
            roleSet = authorizationMap.get(OTHER_ACTIONS_XML_ATTRIBUTE);
        }

        return roleSet.contains(role == null ? Role.GUEST : role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Role> getRolesByPath(String path) {
        Set<Role> roles = authorizationMap.get(path);
        if (roles != null) {
            return Collections.unmodifiableSet(roles);
        }

        return Collections.emptySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getPathsByRole(Role role) {
        Set<String> paths = new HashSet<String>();
        for (Entry<String, Set<Role>> entry : authorizationMap.entrySet()) {
            if (entry.getValue().contains(role)) {
                paths.add(entry.getKey());
            }
        }
        if (!paths.isEmpty()) {
            return Collections.unmodifiableSet(paths);
        }

        return Collections.emptySet();
    }

    /**
     * @param uri
     *            URI
     * @return a path without parameters, e.g. if {@code uri} is "/articles/13" then this method returns "/articles";
     *         returns an original {@code uri} otherwise
     */
    protected String processUri(String uri) {
        Matcher matcher = uriPattern.matcher(uri);
        if (matcher.matches()) {
            return matcher.group(1);
        }

        return uri;
    }

    private void init(InputStream xml, final InputStream dtd) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        if (dtd != null) {
            builder.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                    return new InputSource(dtd);
                }
            });
        }
        Document document = builder.parse(xml);
        Element root = document.getDocumentElement();

        NodeList actionList = root.getChildNodes();
        for (int i = 0; i < actionList.getLength(); i++) {
            parseAction(actionList.item(i));
        }

        xml.close();
        dtd.close();
    }

    private void parseAction(Node action) {
        Set<Role> roleSet = new HashSet<Role>();
        NodeList roleList = action.getChildNodes();
        for (int i = 0; i < roleList.getLength(); i++) {
            String roleName = roleList.item(i).getAttributes().getNamedItem(NAME_XML_ATTRIBUTE).getNodeValue();
            Role role = Role.lookUp(roleName);
            if (role != null) {
                roleSet.add(role);
            }
        }

        String path = null;
        if (action.hasAttributes()) {
            path = action.getAttributes().getNamedItem(PATH_XML_ATTRIBUTE).getNodeValue();
        } else {
            path = OTHER_ACTIONS_XML_ATTRIBUTE;
        }

        authorizationMap.put(path, roleSet);
    }

}
