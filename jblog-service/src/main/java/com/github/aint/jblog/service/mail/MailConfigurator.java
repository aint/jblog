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

import com.github.aint.jblog.service.mail.impl.PropertiesMailConfigurator;

/**
 * This interface represents methods for a configuration of senders of email messages.
 * 
 * @author Olexandr Tyshkovets
 * @see PropertiesMailConfigurator
 */
public interface MailConfigurator {
    /**
     * @return SMTP host
     */
    String getSmtpHost();

    /**
     * Default port: 25, port for {@code TLS/STARTTLS}: 587, port for {@code SSL}: 465.
     * 
     * @return SMTP port
     */
    int getSmtpPort();

    /**
     * @return a sender's email address
     */
    String getSenderEmail();

    /**
     * @return a sender's password
     */
    String getSenderPassword();

}
