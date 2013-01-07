package com.github.aint.jblog.model.dao;

import com.github.aint.jblog.model.entity.Entity;

/**
 * This interface represents the method to check the existence of objects in a data source.
 * 
 * @author Olexandr Tyshkovets
 */
public interface ValidatorDao {
    /**
     * Checks whether exists the {@code entity} with the given {@code value} of the {@code field}.
     * 
     * @param entity
     *            the entity which will be checked
     * @param field
     *            the field which will be checked
     * @param value
     *            the value which will be checked
     * @return {@code true} if the {@code entity} with the given {@code value} of the {@code field} exists;
     *         {@code false} otherwise
     */
    <T> boolean isExist(Class<? extends Entity> entity, String field, T value);

}
