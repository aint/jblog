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
package com.github.aint.jblog.service.security;

import java.util.Set;

import com.github.aint.jblog.model.entity.Role;
import com.github.aint.jblog.service.security.impl.XmlSecurityService;

/**
 * This interface represents methods for authorization operations.
 * 
 * @author Olexandr Tyshkovets
 * @see XmlSecurityService
 */
public interface SecurityService {
    /**
     * Checks authorization for the given {@code path} to the web page.
     * 
     * @param path
     *            the path to the requested web page
     * @param role
     *            the user's role
     * @return {@code true} if an access is allowed; {@code false} otherwise - if an access is denied
     * @throws IllegalArgumentException
     *             if {@code path} is {@code null}
     * @throws IllegalArgumentException
     *             if {@code role} is {@code null}
     * @see Role
     */
    boolean isAuthorize(String path, Role role);

    /**
     * Returns all allowed roles for the given {@code path} to the web page.
     * 
     * @param path
     *            the path to the requested web page
     * @return an unmodifiable view of a {@code Set} of the role or an empty {@code Set} if there's none.
     * @throws IllegalArgumentException
     *             if {@code path} is {@code null}
     * @see Role
     */
    Set<Role> getRolesByPath(String path);

    /**
     * Returns all allowed paths for the given {@code role}.
     * 
     * @param role
     *            the user's role
     * @return an unmodifiable view of a {@code Set} of paths to web pages or an empty {@code Set} if there's none.
     * @throws IllegalArgumentException
     *             if {@code role} is {@code null}
     * @see Role
     */
    Set<String> getPathsByRole(Role role);

}
