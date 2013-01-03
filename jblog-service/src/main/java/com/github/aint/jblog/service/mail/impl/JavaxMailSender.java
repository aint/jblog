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

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.github.aint.jblog.service.exception.mail.MailingException;
import com.github.aint.jblog.service.mail.MailConfigurator;
import com.github.aint.jblog.service.mail.MailSender;

/**
 * This class is {@code javax.mail} implementation of the {@code MailSender} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see MailSender
 */
public class JavaxMailSender implements MailSender {
    private final String smtpHost;
    private final int smtpPort;
    private final String senderEmail;
    private final String senderPassword;

    /**
     * Constructs a {@code JavaxMailSender} with the given {@code configurator}.
     * 
     * @param configurator
     *            this object configures {@code JavaxMailSender}
     * @throws IllegalArgumentException
     *             if {@code configurator} is {@code null}
     * @see MailConfigurator
     */
    public JavaxMailSender(MailConfigurator configurator) {
        if (configurator == null) {
            throw new IllegalArgumentException("configurator can't be null");
        }
        smtpHost = configurator.getSmtpHost();
        smtpPort = configurator.getSmtpPort();
        senderEmail = configurator.getSenderEmail();
        senderPassword = configurator.getSenderPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendEmail(String to, String subject, String content, boolean type) throws MailingException {
        if (to == null) {
            throw new IllegalArgumentException("adrress can't be null");
        }
        if (subject == null) {
            throw new IllegalArgumentException("subject can't be null");
        }
        if (content == null) {
            throw new IllegalArgumentException("content can't be null");
        }

        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.starttls.enable", "true"); // only for GMail
        mailProps.setProperty("mail.smtp.host", smtpHost);
        try {
            mailProps.setProperty("mail.smtp.port", String.valueOf(smtpPort));
        } catch (NumberFormatException e) {
            throw new MailingException("mail.smtp.port is not number", e);
        }
        mailProps.setProperty("mail.smtp.auth", "true");

        Session mailSession = Session.getDefaultInstance(mailProps, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
        MimeMessage mimeMsg = new MimeMessage(mailSession);
        try {
            mimeMsg.setFrom(new InternetAddress(senderEmail));
            mimeMsg.setRecipient(Message.RecipientType.TO, new InternetAddress(to.trim().toLowerCase()));
            mimeMsg.setSubject(subject, "UTF-8");
            if (type) {
                mimeMsg.setContent(content, "text/html; charset=UTF-8");
            } else {
                mimeMsg.setText(content, "UTF-8");
            }
            mimeMsg.setSentDate(new java.util.Date());
            Transport.send(mimeMsg);
        } catch (MessagingException e) {
            throw new MailingException("Mail sending failed", e);
        }
    }

}
