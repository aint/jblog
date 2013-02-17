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

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.aint.jblog.model.dao.ValidatorDao;
import com.github.aint.jblog.model.entity.Entity;

/**
 * @author Olexandr Tyshkovets
 */
public class ExistsValidatorTest {
    private ExistsValidator validator;
    @Mock
    private ValidatorDao validatorDao;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
        validator = new ExistsValidator(validatorDao);
    }

    @Test
    public void exists() {
        when(validatorDao.isExist(Matchers.<Class<Entity>> any(), anyString(), anyObject())).thenReturn(true);

        assertTrue(validator.isValid("some_value", null));
    }

    @Test
    public void notExists() {
        when(validatorDao.isExist(Matchers.<Class<Entity>> any(), anyString(), anyObject())).thenReturn(false);

        assertFalse(validator.isValid("some_value", null));
    }

    @Test
    public void nullValue() {
        assertFalse(validator.isValid(null, null));
    }

}
