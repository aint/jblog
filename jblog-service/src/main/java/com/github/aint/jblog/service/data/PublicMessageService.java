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

import com.github.aint.jblog.model.entity.AnonymousMessage;
import com.github.aint.jblog.service.data.impl.PublicMessageServiceImpl;

/**
 * This interface represents methods to operate with an {@link AnonymousMessage}.
 * 
 * @author Olexandr Tyshkovets
 * @see GenericEntityService
 * @see PublicMessageServiceImpl
 */
public interface PublicMessageService extends GenericEntityService<AnonymousMessage> {
    /**
     * Adds a new {@link AnonymousMessage}.
     * 
     * @param message
     *            an {@link AnonymousMessage} which will be added
     * @return an added {@link AnonymousMessage}
     */
    AnonymousMessage add(AnonymousMessage message);

}
