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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.aint.jblog.service.validation.annotation.FieldMatch;

/**
 * @author Olexandr Tyshkovets
 */
public class FieldMatchValidatorTest {
    private Validator validator;

    @BeforeClass
    public void beforeMethod() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void isValidTrue() {
        Set<ConstraintViolation<TestDto>> constraintViolations = validator.validate(new TestDto("value", "value"));

        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void isValidFalse() {
        Set<ConstraintViolation<TestDto>> constraintViolations = validator.validate(new TestDto("value", "not_value"));

        assertEquals(constraintViolations.size(), 1);
        assertEquals(constraintViolations.iterator().next().getMessage(), "Oh, shi~! Fields don't match.");
    }

    @Test
    public void isValidNullTrue() {
        Set<ConstraintViolation<TestDto>> constraintViolations = validator.validate(new TestDto(null, null));

        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void isValidNullFalse() {
        Set<ConstraintViolation<TestDto>> constraintViolations = validator.validate(new TestDto(null, "value"));

        assertEquals(constraintViolations.size(), 1);
        assertEquals(constraintViolations.iterator().next().getMessage(), "Oh, shi~! Fields don't match.");
    }

    @FieldMatch(field = "field1", matchField = "field2", message = "Oh, shi~! Fields don't match.")
    public class TestDto {
        String field1;
        String field2;

        public TestDto(String field1, String field2) {
            this.field1 = field1;
            this.field2 = field2;
        }

        public String getField1() {
            return field1;
        }

        public String getField2() {
            return field2;
        }
    }

}
