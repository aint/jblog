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

/**
 * @author Olexandr Tyshkovets
 */
public final class CookieConstants {
    public static final int USER_LANGUAGE_CODE_COOKIE_LIFETIME = 60 * 60 * 24 * 365 * 5;
    public static final String USER_LANGUAGE_CODE_COOKIE_NAME = "userLanguageCode";
    public static final int AUTHENTICATION_COOKIE_LIFETIME = 60 * 60 * 24 * 365 * 5;
    public static final String AUTHENTICATION_COOKIE_NAME = "authenticationUserName";

    private CookieConstants() {
    }

}
