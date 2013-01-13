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
package com.github.aint.jblog.web.dto;

import java.util.Calendar;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.github.aint.jblog.model.entity.Language;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.validation.annotation.FieldMatch;
import com.github.aint.jblog.service.validation.annotation.Unique;

/**
 * DTO for a {@link User} entity. Used for validation of a {@link User}'s data at register operation.
 * 
 * @author Olexandr Tyshkovets
 * @see FieldMatch
 * @see Unique
 */
@FieldMatch(field = "password", matchField = "rePassword", message = "{register_user.password.confirmation}")
public class RegisterUserDto {
    @NotNull(message = "{register_user.username.not_null}")
    @Size(min = User.USERNAME_MIN_LENGTH, max = User.USERNAME_MAX_LENGTH, message = "{register_user.username.length}")
    @Pattern(regexp = "[а-яА-Я\\w]*", message = "{register_user.username.pattern}")
    @Unique(entity = User.class, field = "userName", message = "{register_user.username.duplicate}")
    private final String userName;
    @NotNull(message = "{register_user.first_name.not_null}")
    @Size(min = User.FIRSTNAME_MIN_LENGTH, max = User.FIRSTNAME_MIN_LENGTH, message = "{register_user.first_name.length}")
    @Pattern(regexp = "[a-zA-Zа-яґєіїёА-ЯҐЄІЇЁ]*", message = "{register_user.first_name.pattern}")
    private final String firstName;
    @NotNull(message = "{register_user.last_name.not_null}")
    @Size(min = User.LASTNAME_MIN_LENGTH, max = User.LASTNAME_MAX_LENGTH, message = "{register_user.last_name.length}")
    @Pattern(regexp = "[[a-zA-Zа-яґєіїёА-ЯҐЄІЇЁ]]*", message = "{register_user.last_name.pattern}")
    private final String lastName;
    @Pattern(regexp = "[\\w-\\.]{4,25}@([a-zA-Z]{1,20}\\.){1,2}[a-z]{2,3}", message = "{register_user.email.pattern}")
    @Unique(entity = User.class, field = "email", message = "{register_user.email.duplicate}")
    private final String email;
    @Size(min = User.PASSWORD_MIN_LENGTH, max = User.PASSWORD_MAX_LENGTH, message = "{register_user.password.length}")
    private final String password;
    private final String rePassword;
    @Pattern(regexp = "[1-9]|[12][0-9]|3[01]", message = "{register_user.day.pattern}")
    private final String day;
    @Pattern(regexp = "[1-9]|1[012]", message = "{register_user.month.pattern}")
    private final String month;
    @Pattern(regexp = "(19|20)\\d\\d", message = "{register_user.year.pattern}")
    private final String year;
    // yes, this field isn't annotated
    private final Language language;

    /**
     * Constructs a {@code RegisterUserDto} with the given parameters. Trims all not-null fields.
     * 
     * @param userName
     *            user's userName
     * @param firstName
     *            user's first name
     * @param lastName
     *            user's last name
     * @param email
     *            user's email
     * @param password
     *            user's password
     * @param rePassword
     *            user's password confirmation
     * @param day
     *            a day of user's birthday
     * @param month
     *            a month of user's birthday
     * @param year
     *            a year of user's birthday
     * @param language
     *            user's language
     * @see User
     */
    public RegisterUserDto(String userName, String firstName, String lastName, String email, String password,
            String rePassword, String day, String month, String year, Language language) {
        this.userName = userName == null ? null : userName.trim();
        this.firstName = firstName == null ? null : firstName.trim();
        this.lastName = lastName == null ? null : lastName.trim();
        this.email = email == null ? null : email.trim();
        this.password = password == null ? null : password.trim();
        this.rePassword = rePassword == null ? null : rePassword.trim();
        this.day = day == null ? null : day.trim();
        this.month = month == null ? null : month.trim();
        this.year = year == null ? null : year.trim();
        this.language = language;
    }

    /**
     * Creates a {@link User}.
     * 
     * @return a created {@link User}
     */
    public User createUser() {
        User user = new User(userName, email, password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
        user.setBirthday(calendar.getTime());
        user.setLanguage(language == null ? Language.ENGLISH : language);

        return user;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the rePassword
     */
    public String getRePassword() {
        return rePassword;
    }

    /**
     * @return the day
     */
    public String getDay() {
        return day;
    }

    /**
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @return the language
     */
    public Language getLanguage() {
        return language;
    }

}
