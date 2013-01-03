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

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.aint.jblog.model.dao.hibernate.UserHibernateDao;
import com.github.aint.jblog.model.entity.Role;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.data.impl.UserServiceImpl;
import com.github.aint.jblog.service.exception.data.UserNotFoundException;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.web.constant.SessionConstant;

/**
 * This tag checks whether the current user is authorized or have the given role.
 * 
 * @author Olexandr Tyshkovets
 */
public class IfAuth extends ConditionalTagSupport {
    private static final long serialVersionUID = 1274396530182303835L;
    private static Logger logger = LoggerFactory.getLogger(IfAuth.class);
    private String roles;

    /**
     * @param roles
     *            the roles to set
     */
    public void setRoles(String roles) {
        this.roles = roles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean condition() throws JspTagException {
        HttpSession session = pageContext.getSession();
        if (session == null) {
            logger.error("The HttpSession is null");
            throw new JspTagException("The HttpSession is null");
        }

        String userName = (String) session.getAttribute(SessionConstant.USER_NAME_SESSION_ATTRIBUTE);
        if (userName == null) {
            return false;
        }

        User user = null;
        try {
            user = new UserServiceImpl(new UserHibernateDao(HibernateUtil.getSessionFactory())).getByUserName(userName);
        } catch (UserNotFoundException e) {
            logger.error("The user not found", e);
            return false;
        }

        if (roles == null) {
            return true; // access for all roles
        }

        String[] rolesArray = roles.split(",");
        for (String role : rolesArray) {
            if (user.getRole().equals(Role.lookUp(role.toUpperCase()))) {
                return true;
            }
        }

        return false;
    }

}
