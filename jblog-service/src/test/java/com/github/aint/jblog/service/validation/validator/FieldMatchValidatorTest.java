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
