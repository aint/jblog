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
package com.github.aint.jblog.service.data;

import com.github.aint.jblog.model.entity.PublicMessage;
import com.github.aint.jblog.service.data.impl.PublicMessageServiceImpl;

/**
 * This interface represents methods to operate with a {@link PublicMessage}.
 * 
 * @author Olexandr Tyshkovets
 * @see GenericEntityService
 * @see PublicMessageServiceImpl
 */
public interface PublicMessageService extends GenericEntityService<PublicMessage> {
    /**
     * Adds a new {@link PublicMessage}.
     * 
     * @param message
     *            a {@link PublicMessage} which will be added
     * @return an added {@link PublicMessage}
     */
    PublicMessage add(PublicMessage message);

}
