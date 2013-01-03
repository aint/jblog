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
package com.github.aint.jblog.service.mail;

import com.github.aint.jblog.service.exception.mail.MailingException;
import com.github.aint.jblog.service.mail.impl.JavaxMailSender;

/**
 * This interface represents a factory method to send an email message.
 * 
 * @author Olexandr Tyshkovets
 * @see JavaxMailSender
 */
public interface MailSender {
    /**
     * This method sends an email message.
     * 
     * @param to
     *            an email address of an addressee
     * @param subject
     *            the headline of an email message
     * @param content
     *            the text of an email message
     * @param type
     *            if {@code true} sets text/html as an email message MIME type; sets text/plain otherwise
     * @throws IllegalArgumentException
     *             if {@code to}, {@code subject} or {@code content} is {@code null}
     * @throws MailingException
     *             if an error has occurred while performing an email message sending operation
     */
    void sendEmail(String to, String subject, String content, boolean type) throws MailingException;

}
