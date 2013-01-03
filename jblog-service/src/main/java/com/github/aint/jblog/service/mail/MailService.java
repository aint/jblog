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

import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.mail.impl.MailServiceImpl;

/**
 * This interface represents methods which send activation, recovery, (notification or subscription - coming soon) email
 * messages.
 * 
 * @author Olexandr Tyshkovets
 * @see MailServiceImpl
 */
public interface MailService {
    /**
     * Sends an email message with a link to activate the {@link User}'s account.
     * 
     * @param recipient
     *            addressee
     */
    void sendAccountActivationMail(User recipient);

    /**
     * Sends an email message with a {@link User}'s password.
     * 
     * @param recipient
     *            addressee
     */
    void sendPasswordRecoveryMail(User recipient);

}
