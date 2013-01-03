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

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.github.aint.jblog.service.exception.other.UserRankNotFoundException;
import com.github.aint.jblog.service.exception.other.XmlUserRankConfigurationException;
import com.github.aint.jblog.service.other.UserRankConfiguration;

/**
 * This class is a XML based implementation of the {@code UserRankConfiguration} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see UserRankConfiguration
 */
public class XmlUserRankConfiguration implements UserRankConfiguration {
    private static final String MIN_RATING_RANGE_XML_ATTRIBUTE = "min";
    private static final String MAX_RATING_RANGE_XML_ATTRIBUTE = "max";
    private static final String RANK_NAME_XML_ATTRIBUTE = "name";
    private NodeList childNodeList;

    /**
     * Constructs a {@code XmlUserRankConfiguration} and reads data from the specified input stream.
     * 
     * @param xml
     *            an {@code InputStream} of a XML file which contains a user's rank configuration data
     * @throws IllegalArgumentException
     *             if {@code xml} is {@code null}
     * @throws XmlUserRankConfigurationException
     *             if an error has occurred while performing parsing
     */
    public XmlUserRankConfiguration(InputStream xml) throws XmlUserRankConfigurationException {
        if (xml == null) {
            throw new IllegalArgumentException("xml can't be null");
        }
        try {
            init(xml, null);
        } catch (Exception e) {
            throw new XmlUserRankConfigurationException(e);
        }
    }

    /**
     * Constructs a {@code XmlUserRankConfiguration} and reads data from the specified input streams.
     * 
     * @param xml
     *            an {@code InputStream} of a XML file which contains a user's rank configuration data
     * @param dtd
     *            an {@code InputStream} of {@code xml}'s DTD file
     * @throws IllegalArgumentException
     *             if {@code xml} is {@code null}
     * @throws IllegalArgumentException
     *             if {@code dtd} is {@code null}
     * @throws XmlUserRankConfigurationException
     *             if an error has occurred while performing parsing
     */
    public XmlUserRankConfiguration(InputStream xml, InputStream dtd) throws XmlUserRankConfigurationException {
        if (xml == null) {
            throw new IllegalArgumentException("xml can't be null");
        }
        if (dtd == null) {
            throw new IllegalArgumentException("dtd can't be null");
        }
        try {
            init(xml, dtd);
        } catch (Exception e) {
            throw new XmlUserRankConfigurationException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRank(int rating) throws XmlUserRankConfigurationException, UserRankNotFoundException {
        for (int i = 0, min = 0, max = 0; i < childNodeList.getLength(); i++) {
            try {
                min = Integer.parseInt(childNodeList.item(i).getAttributes()
                        .getNamedItem(MIN_RATING_RANGE_XML_ATTRIBUTE).getNodeValue());
                max = Integer.parseInt(childNodeList.item(i).getAttributes()
                        .getNamedItem(MAX_RATING_RANGE_XML_ATTRIBUTE).getNodeValue());
            } catch (NumberFormatException e) {
                throw new XmlUserRankConfigurationException(e);
            }

            if (min <= rating && rating <= max) {
                return childNodeList.item(i).getFirstChild().getAttributes().getNamedItem(RANK_NAME_XML_ATTRIBUTE)
                        .getNodeValue();
            }
        }

        throw new UserRankNotFoundException("rating = " + rating);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinRange(String rank) throws XmlUserRankConfigurationException {
        if (rank == null) {
            throw new IllegalArgumentException("rank can't be null");
        }

        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getFirstChild().getAttributes().getNamedItem(RANK_NAME_XML_ATTRIBUTE)
                    .getNodeValue().equals(rank)) {
                try {
                    return Integer.parseInt(childNodeList.item(i).getAttributes()
                            .getNamedItem(MIN_RATING_RANGE_XML_ATTRIBUTE).getNodeValue());
                } catch (NumberFormatException e) {
                    throw new XmlUserRankConfigurationException(e);
                }
            }
        }

        return Integer.MIN_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxRange(String rank) throws XmlUserRankConfigurationException {
        if (rank == null) {
            throw new IllegalArgumentException("rank can't be null");
        }

        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getFirstChild().getAttributes().getNamedItem(RANK_NAME_XML_ATTRIBUTE)
                    .getNodeValue().equals(rank)) {
                try {
                    return Integer.parseInt(childNodeList.item(i).getAttributes()
                            .getNamedItem(MAX_RATING_RANGE_XML_ATTRIBUTE).getNodeValue());
                } catch (NumberFormatException e) {
                    throw new XmlUserRankConfigurationException(e);
                }
            }
        }

        return Integer.MAX_VALUE;
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
        this.childNodeList = root.getChildNodes();

        xml.close();
        dtd.close();
    }

}
