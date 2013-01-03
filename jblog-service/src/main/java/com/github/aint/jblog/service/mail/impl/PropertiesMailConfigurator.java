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
package com.github.aint.jblog.service.mail.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.github.aint.jblog.service.mail.MailConfigurator;

/**
 * This class is a properties based implementation of the {@code MailSenderConfigurator}.
 * 
 * @author Olexandr Tyshkovets
 * @see MailConfigurator
 * @see JavaxMailSender
 */
public class PropertiesMailConfigurator implements MailConfigurator {
    private final String smtpHost;
    private final int smtpPort;
    private final String senderEmail;
    private final String senderPassword;

    /**
     * Constructs {@code PropertiesMailSenderConfigurator} with the given path to a properties file.
     * 
     * @param mailConfigProperties
     *            an {@code InputStream} of a properties file which contains a configuration data
     * @throws IllegalArgumentException
     *             if {@code mailConfigProperties} is {@code null}
     * @throws IOException
     *             if error occurred in I/O
     */
    public PropertiesMailConfigurator(InputStream mailConfigProperties) throws IOException {
        if (mailConfigProperties == null) {
            throw new IllegalArgumentException("mailConfigProperties can't be null");
        }
        Properties prop = new Properties();
        prop.load(mailConfigProperties);
        smtpHost = prop.getProperty("mail.smtp.host");
        smtpPort = Integer.parseInt(prop.getProperty("mail.smtp.port"));
        senderEmail = prop.getProperty("mail.sender.email");
        senderPassword = prop.getProperty("mail.sender.password");

        mailConfigProperties.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSmtpHost() {
        return smtpHost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSmtpPort() {
        return smtpPort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSenderEmail() {
        return senderEmail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSenderPassword() {
        return senderPassword;
    }

}
