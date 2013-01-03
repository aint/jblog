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
package com.github.aint.jblog.model.entity;

/**
 * This enum lists a {@link User}'s roles.
 * 
 * @author Olexandr Tyshkovets
 * @see User#getRole()
 */
public enum Role {
    /**
     * The guest role.
     */
    GUEST("guest"),
    /**
     * The user role.
     */
    USER("user"),
    /**
     * The moderator role.
     */
    MODERATOR("moderator"),
    /**
     * The administrator role.
     */
    ADMIN("admin");

    private String roleName;

    /**
     * Constructs a {@code Role} with the given {@code roleName}.
     * 
     * @param roleName
     *            the role's name
     */
    private Role(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Returns the lookup {@code Role} by the given {@code roleName}. Lookups with ignoring case.
     * 
     * @param roleName
     *            the role's name
     * @return a {@code Role} by the given {@code roleName}; {@code null} otherwise
     */
    public static Role lookUp(String roleName) {
        for (Role role : values()) {
            if (role.getRoleName().equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        return null;
    }

    /**
     * @return a role's name
     */
    public String getRoleName() {
        return roleName;
    }

}
