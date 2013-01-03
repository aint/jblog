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
package com.github.aint.jblog.web.view.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * This tag prints validation error messages, received by the name of the field.
 * 
 * @author Olexandr Tyshkovets
 */
public class PrintErrorValidationMsg extends TagSupport {
    private static final long serialVersionUID = -2345022001144650141L;
    private String fieldName;
    private Map<String, String> errorMsgMap;

    /**
     * @param fieldName
     *            the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @param errorMsgMap
     *            the errorMsgMap to set
     */
    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int doStartTag() throws JspException {
        if (errorMsgMap != null && !fieldName.isEmpty()) {
            for (Map.Entry<String, String> entry : errorMsgMap.entrySet()) {
                if (entry.getKey().equals(fieldName)) {
                    JspWriter out = pageContext.getOut();
                    try {
                        out.print(entry.getValue());
                    } catch (IOException e) {
                        throw new JspException(e);
                    }
                }
            }
        }

        return SKIP_BODY;
    }

}
