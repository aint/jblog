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
import com.github.aint.jblog.service.validation.annotation.Unique;

/**
 * Checks the field's value of some entity for the uniqueness in a data source.
 * 
 * @author Olexandr Tyshkovets
 * @see Unique
 * @see Entity
 */
public class UniqueValidator implements ConstraintValidator<Unique, String> {
    private Class<? extends Entity> entity;
    private ValidatorDao validatorDao;
    private String field;

    /**
     * Constructs a {@code UniqueValidator}.
     */
    public UniqueValidator() {
        validatorDao = new ValidatorHibernateDao(HibernateUtil.getSessionFactory());
    }

    /**
     * Constructs a {@code UniqueValidator} with the given {@code validatorDao}.
     * 
     * @param validatorDao
     *            dao for performing validation operations in a data source
     */
    public UniqueValidator(ValidatorDao validatorDao) {
        this.validatorDao = validatorDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(Unique annotation) {
        this.entity = annotation.entity();
        this.field = annotation.field();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value != null) && !(validatorDao.isExist(entity, field, value));
    }

}
