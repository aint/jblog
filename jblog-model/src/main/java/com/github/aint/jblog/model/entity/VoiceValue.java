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
 * This enum lists values of a voice.
 * 
 * @author Olexandr Tyshkovets
 * @see AbstractVoice#getValue()
 */
public enum VoiceValue {
    /**
     * The negative voice.
     */
    NEGATIVE("minus"),
    /**
     * The positive voice.
     */
    POSITIVE("plus");

    private String value;

    /**
     * Constructs a {@code VoiceValue} with the given {@code value}.
     * 
     * @param value
     *            the voice's value
     */
    private VoiceValue(String value) {
        this.value = value;
    }

    /**
     * Returns the lookup {@code VoiceValue} by the given {@code value}. Lookups with ignoring case.
     * 
     * @param value
     *            the voice's value
     * @return a {@code VoiceValue} by the given {@code value}; {@code null} otherwise
     */
    public static VoiceValue lookUp(String value) {
        for (VoiceValue voiceValue : values()) {
            if (voiceValue.getValue().equalsIgnoreCase(value)) {
                return voiceValue;
            }
        }
        return null;
    }

    /**
     * @return a voice's value
     */
    public String getValue() {
        return value;
    }

}
