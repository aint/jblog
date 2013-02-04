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
package com.github.aint.jblog.service.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.aint.jblog.model.dao.ValidatorDao;
import com.github.aint.jblog.model.dao.hibernate.ValidatorHibernateDao;
import com.github.aint.jblog.model.entity.Entity;
import com.github.aint.jblog.service.util.HibernateUtil;
import com.github.aint.jblog.service.validation.annotation.Exists;

/**
 * Checks the field's value for the existence in a data source.
 * 
 * @author Olexandr Tyshkovets
 * @see Exists
 * @see Entity
 */
public class ExistsValidator implements ConstraintValidator<Exists, String> {
    private Class<? extends Entity> entity;
    private ValidatorDao validatorDao;
    private String field;

    /**
     * Constructs a {@code UniqueValidator}.
     */
    public ExistsValidator() {
        validatorDao = new ValidatorHibernateDao(HibernateUtil.getSessionFactory());
    }

    /**
     * Constructs a {@code UniqueValidator} with the given {@code validatorDao}.
     * 
     * @param validatorDao
     *            DAO for performing validation operations in a data source
     */
    public ExistsValidator(ValidatorDao validatorDao) {
        this.validatorDao = validatorDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(Exists annotation) {
        this.entity = annotation.entity();
        this.field = annotation.field();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value != null) && (validatorDao.isExist(entity, field, value));
    }

}
