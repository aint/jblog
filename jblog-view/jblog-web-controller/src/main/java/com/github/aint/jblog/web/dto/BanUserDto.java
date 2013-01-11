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
package com.github.aint.jblog.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.github.aint.jblog.model.entity.User;

/**
 * DTO for a {@link User} entity. Used for validation of a {@link User}'s data at ban operation.
 * 
 * @author Olexandr Tyshkovets
 */
public class BanUserDto {
    @NotNull(message = "")
    @Size(min = User.USERNAME_MIN_LENGTH, max = User.USERNAME_MAX_LENGTH, message = "")
    @Pattern(regexp = "[а-яА-Я\\w]*", message = "")
    private String userName;
    @NotNull(message = "")
    @Pattern(regexp = "[1-9]|[1-9][0-9]", message = "")
    private String banLength;
    @NotNull(message = "")
    @Size(min = 3, max = 70, message = "")
    private String banReason;

    /**
     * Constructs a {@code BanUserDto} with the given parameters. Trims all not-null fields.
     * 
     * @param userName
     *            the user's userName
     * @param banLength
     *            the length of the ban in days
     * @param banReason
     *            the ban reason
     * @see User
     */
    public BanUserDto(String userName, String banLength, String banReason) {
        this.userName = userName == null ? null : userName.trim();
        this.banLength = banLength == null ? null : banLength.trim();
        this.banReason = banReason == null ? null : banReason.trim();
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the banLength
     */
    public String getBanLength() {
        return banLength;
    }

    /**
     * @return the banReason
     */
    public String getBanReason() {
        return banReason;
    }

}
