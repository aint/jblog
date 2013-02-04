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
import javax.validation.constraints.Size;

import com.github.aint.jblog.model.entity.Hub;
import com.github.aint.jblog.service.validation.annotation.Unique;

/**
 * DTO for a {@link Hub} entity. Used for validation of a {@link Hub}'s data.
 * 
 * @author Olexandr Tyshkovets
 */
public class HubDto {

    @NotNull(message = "{hub.name.not_null}")
    @Size(min = Hub.NAME_MIN_LENGTH, max = Hub.NAME_MAX_LENGTH, message = "{hub.name.length}")
    @Unique(entity = Hub.class, field = "name", message = "{hub.name.duplicate}")
    private String name;

    @NotNull(message = "{hub.description.not_null}")
    @Size(min = Hub.DESCRIPTION_MIN_LENGTH, max = Hub.DESCRIPTION_MAX_LENGTH, message = "{hub.description.length}")
    private String description;

    @NotNull(message = "{hub.type.not_null}")
    private String type;

    /**
     * Constructs a {@code HubDto} with the given parameters. Trims all not-null fields.
     * 
     * @param name
     *            the hub's name
     * @param type
     *            the hub's type
     * @see Hub
     */
    public HubDto(String name, String description, String type) {
        this.name = name == null ? null : name.trim();
        this.description = description == null ? null : description.trim();
        this.type = type == null ? null : type.trim();
    }

    /**
     * Creates a {@link Hub}.
     * 
     * @return a created {@link Hub}.
     */
    public Hub createHub() {
        return new Hub(name, description, type.equals("personal"));
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

}
