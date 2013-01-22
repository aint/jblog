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

import java.io.IOException;
import java.util.Properties;

/**
 * This class represents {@code PATH} and {@code MAPPING} constants which store paths to {@code JSPs} and URL mapping of
 * {@code JSPs}, respectively. These constants configured by {@code mapping.properties} and {@code path.properties},
 * respectively.
 * 
 * @author Olexandr Tyshkovets
 */
public final class ConstantHolder {
    /**
     * This constant stores paths to {@code JSPs}.
     */
    public static final Properties PATH;
    /**
     * This constant stores URL mapping of {@code JSPs}.
     */
    public static final Properties MAPPING;

    static {
        PATH = new Properties();
        MAPPING = new Properties();
        try {
            PATH.load(ConstantHolder.class.getResourceAsStream("path.properties"));
            MAPPING.load(ConstantHolder.class.getResourceAsStream("mapping.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ConstantHolder() {
    }

}
