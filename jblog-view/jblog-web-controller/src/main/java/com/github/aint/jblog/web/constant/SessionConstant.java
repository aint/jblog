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
package com.github.aint.jblog.web.constant;

import com.github.aint.jblog.model.entity.Language;
import com.github.aint.jblog.model.entity.User;

/**
 * This class represents some constants for storing in a HTTP session.
 * 
 * @author Olexandr Tyshkovets
 */
public final class SessionConstant {
    /**
     * Represents a {@code username} of the current user in a session.
     * 
     * @see User#getUserName()
     */
    public static final String USER_NAME_SESSION_ATTRIBUTE = "userName";
    /**
     * Represents a {@code language} of the current user in a session.
     * 
     * @see Language
     * @see User#getLanguage()
     */
    public static final String USER_LANGUAGE_SESSION_ATTRIBUTE = "userLanguage";

    private SessionConstant() {
    }

}
