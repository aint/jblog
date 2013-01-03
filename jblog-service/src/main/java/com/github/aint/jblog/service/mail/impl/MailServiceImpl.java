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
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.exception.mail.MailingException;
import com.github.aint.jblog.service.mail.MailSender;
import com.github.aint.jblog.service.mail.MailService;

/**
 * This class implementing the {@code MailService} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see MailService
 */
public class MailServiceImpl implements MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    private static final String ACCOUNT_ACTIVATION_HTML_TEMPLATE_PATH = "com/github/aint/jblog/service/mail" +
            "/template/html/accountActivation.vm";
    private static final String MAIL_MESSAGES_TEMPLATES_PATH = "com.github.aint.jblog.service.mail.i18n" +
            ".templateMailMessages";
    private static final String ACTIVATE_ACCOUNT_URL_PATTERN = "/activate-account";
    private static final String ACCOUNT_ACTIVATION_IMG_PATH = "/resources/images/accountActivation.jpg";
    private final MailSender mailSender;
    private final VelocityEngine velocityEngine;
    private final String applicationUrl;

    /**
     * Constructs a {@code MailServiceImpl} with the given parameters.
     * 
     * @param mailSender
     *            the {@code MailSender}'s implementation which sends email messages
     * @param velocityEngine
     *            the engine for templating email messages
     * @param applicationUrl
     *            the application's URL, e.g. "http://myapplication.com/"
     */
    public MailServiceImpl(MailSender mailSender, VelocityEngine velocityEngine, String applicationUrl) {
        this.mailSender = mailSender;
        this.velocityEngine = velocityEngine;
        this.applicationUrl = applicationUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendAccountActivationMail(User recipient) {
        VelocityContext veloContext = new VelocityContext();
        ResourceBundle mailBoundle = ResourceBundle.getBundle(MAIL_MESSAGES_TEMPLATES_PATH,
                recipient.getLanguage().getLocale());
        String appName = mailBoundle.getString("application_name");

        veloContext.put("application_name", appName);
        veloContext.put("account_activation_subject",
                MessageFormat.format(mailBoundle.getString("account_activation.subject"), appName));
        veloContext.put("account_activation_img_path", applicationUrl + ACCOUNT_ACTIVATION_IMG_PATH);
        veloContext.put("greeting", MessageFormat.format(mailBoundle.getString("greeting"), recipient.getUserName()));
        veloContext.put("account_activation_content_part1", MessageFormat.format(
                mailBoundle.getString("account_activation.content.part1"), applicationUrl, appName));
        veloContext.put("account_activation_content_part2", mailBoundle.getString("account_activation.content.part2"));
        veloContext.put("account_activation_content_part3",
                MessageFormat.format(mailBoundle.getString("account_activation.content.part3"),
                        applicationUrl + ACTIVATE_ACCOUNT_URL_PATTERN + "/" + recipient.getUuid()));
        veloContext.put("account_activation_content_part4", mailBoundle.getString("account_activation.content.part4"));
        veloContext.put("account_activation_content_part5", mailBoundle.getString("account_activation.content.part5"));
        veloContext.put("wish", MessageFormat.format(mailBoundle.getString("wish"), appName));

        Template veloTemp = velocityEngine.getTemplate(ACCOUNT_ACTIVATION_HTML_TEMPLATE_PATH, "UTF-8");
        StringWriter sw = new StringWriter();
        veloTemp.merge(veloContext, sw);
        logger.debug("account activation mail's body {}", sw.toString());
        try {
            mailSender.sendEmail(recipient.getEmail(), MessageFormat.format(
                    mailBoundle.getString("account_activation.subject"), appName), sw.toString(), true);
        } catch (MailingException e) {
            logger.error("Failed to sent activation email message to user " + recipient.getUserName(), e);
        }

        if (sw != null) {
            try {
                sw.close();
            } catch (IOException e) {
                logger.warn("Some error occurred in I/O while StringWriter closing", e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPasswordRecoveryMail(User recipient) {
        // not yet
        throw new UnsupportedOperationException("Not yet");
    }

}
