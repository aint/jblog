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
public class UniqueValidatorTest {
    private UniqueValidator validator;
    @Mock
    private ValidatorDao validatorDao;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
        validator = new UniqueValidator(validatorDao);
    }

    @Test
    public void isValidTrue() {
        when(validatorDao.isExist(Matchers.<Class<Entity>> any(), anyString(), anyObject())).thenReturn(false);

        assertTrue(validator.isValid("some_value", null));
    }

    @Test
    public void isValidFalse() {
        when(validatorDao.isExist(Matchers.<Class<Entity>> any(), anyString(), anyObject())).thenReturn(true);

        assertFalse(validator.isValid("some_nvalue", null));
    }

}
