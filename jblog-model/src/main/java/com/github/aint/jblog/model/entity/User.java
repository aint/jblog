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
package com.github.aint.jblog.model.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class stores the information about a user.
 * 
 * @author Olexandr Tyshkovets
 * @see Entity
 */
@javax.persistence.Entity
@Table(name = "USER")
@NamedQueries({
        @NamedQuery(name = "User.getByUserName", query = "SELECT u FROM User u WHERE u.userName = ?"),
        @NamedQuery(name = "User.getByUuid", query = "SELECT u FROM User u WHERE u.uuid = ?"),
        @NamedQuery(name = "User.getByEmail", query = "SELECT u FROM User u WHERE u.email = ?"),
        @NamedQuery(name = "User.getNonActivatedUsers", query = "SELECT u FROM User u WHERE u.activated = false"),
        @NamedQuery(name = "User.getAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.getAllOnPageOrderByAsc", query = "SELECT u FROM User u ORDER BY u.id ASC"),
        @NamedQuery(name = "User.getAllOnPageOrderByDesc", query = "SELECT u FROM User u ORDER BY u.id DESC"),
        @NamedQuery(name = "User.getCount", query = "SELECT COUNT(*) FROM User"),
        @NamedQuery(name = "User.deleteById", query = "DELETE User u WHERE u.id = ?"),
})
public class User implements Entity, Comparable<User> {
    private static final long serialVersionUID = 5111034367628693026L;
    private Long id;
    private String uuid = UUID.randomUUID().toString();
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String salt;
    private String rank = DEFAULT_RANK;
    private int rating;
    private boolean activated;
    private String banReason;
    private Date banExpirationDate;
    private Date birthday;
    private Date registrationDate;
    private Date lastLoginTime;
    private Language language = DEFAULT_LANGUAGE;
    private Role role = Role.USER;
    private Set<Article> articles = new HashSet<Article>();
    private Set<Comment> comments = new HashSet<Comment>();

    /**
     * The minimum length of the username.
     */
    public static final int USERNAME_MIN_LENGTH = 3;
    /**
     * The maximum length of the username.
     */
    public static final int USERNAME_MAX_LENGTH = 20;
    /**
     * The minimum length of the firstname.
     */
    public static final int FIRSTNAME_MIN_LENGTH = 2;
    /**
     * The maximum length of the firstname.
     */
    public static final int FIRSTNAME_MAX_LENGTH = 30;
    /**
     * The minimum length of the lastname.
     */
    public static final int LASTNAME_MIN_LENGTH = 2;
    /**
     * The maximum length of the lastname.
     */
    public static final int LASTNAME_MAX_LENGTH = 30;
    /**
     * The minimum length of the password.
     */
    public static final int PASSWORD_MIN_LENGTH = 4;
    /**
     * The maximum length of the password.
     */
    public static final int PASSWORD_MAX_LENGTH = 50;
    /**
     * The default user's rank.
     */
    public static final String DEFAULT_RANK = "user.rank.rookie";
    /**
     * The default user's language
     */
    public static final Language DEFAULT_LANGUAGE = Language.ENGLISH;

    /**
     * The default constructor for hibernate.
     */
    protected User() {
    }

    /**
     * Constructs a {@code User} with required fields.
     * 
     * @param userName
     *            the user's name
     * @param email
     *            the user's email
     * @param password
     *            the user's password
     */
    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return user the universally unique identifier
     */
    @Column(name = "USER_UUID", unique = true, nullable = false)
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid
     *            the universally unique identifier to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the user name
     */
    @Column(name = "USER_NAME", unique = true, nullable = false)
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the user name to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return user the first name
     */
    @Column(name = "FIRST_NAME", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the last name
     */
    @Column(name = "LAST_NAME", nullable = false)
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    @Column(name = "EMAIL", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    @Column(name = "PASSWORD", nullable = false)
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the salt
     */
    @Column(name = "SALT", nullable = false)
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt
     *            the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return the rank
     */
    @Column(name = "RANK", nullable = false)
    public String getRank() {
        return rank;
    }

    /**
     * @param rank
     *            the rank to set
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /**
     * The sum of all ratings of comments and articles of the user.
     * 
     * @return user rating
     * @see Comment#getRating()
     * @see Article#getRating()
     */
    @Column(name = "RATING", nullable = false)
    public int getRating() {
        return rating;
    }

    /**
     * @param rating
     *            the rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * @return {@code true} if the user is activated
     */
    @Column(name = "ACTIVATED", nullable = false)
    public boolean isActivated() {
        return activated;
    }

    /**
     * @param activated
     *            {@code true} if user must be activated; {@code false} otherwise
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
     * @return the ban reason
     */
    @Column(name = "BAN_REASON", nullable = true)
    public String getBanReason() {
        return banReason;
    }

    /**
     * @param banReason
     *            the ban reason to set
     */
    public void setBanReason(String banReason) {
        this.banReason = banReason;
    }

    /**
     * @return the ban expiration date
     */
    @Column(name = "BAN_EXPIRATION_DATE", columnDefinition = "DATETIME", nullable = true)
    public Date getBanExpirationDate() {
        return banExpirationDate;
    }

    /**
     * @param banExpirationDate
     *            the ban expiration date to set
     */
    public void setBanExpirationDate(Date banExpirationDate) {
        this.banExpirationDate = banExpirationDate;
    }

    /**
     * @return the birthday
     */
    @Column(name = "BIRTHDAY", columnDefinition = "DATE", nullable = false)
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     *            the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the registration date
     */
    @Column(name = "REGISTRATION_DATE", columnDefinition = "DATETIME", nullable = false)
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * @param registrationDate
     *            the registration date to set
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * @return the last login time
     */
    @Column(name = "LAST_LOGIN_TIME", columnDefinition = "DATETIME", nullable = true)
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime
     *            the last login time to set
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return the language
     * @see Language
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "LANGUAGE", nullable = false)
    public Language getLanguage() {
        return language;
    }

    /**
     * @param language
     *            the language to set
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * @return the role
     * @see Role
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    public Role getRole() {
        return role;
    }

    /**
     * @param role
     *            the role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * @return set articles of user
     * @see Article
     */
    @OneToMany(mappedBy = "author")
    public Set<Article> getArticles() {
        return articles;
    }

    /**
     * @param articles
     *            articles of user
     */
    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    /**
     * @return set comments of user
     * @see Comment
     */
    @OneToMany(mappedBy = "author")
    public Set<Comment> getComments() {
        return comments;
    }

    /**
     * @param comments
     *            comments of user
     */
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Compares two users for ordering. Compares by {@code uuid}, {@code userName} and {@code email}.
     * 
     * @param user
     *            the user to be compared
     * @return a negative integer, zero, or a positive integer as this user is less than, equal to, or greater than the
     *         specified {@code user}
     * @throws NullPointerException
     *             if the specified {@code user} is null
     */
    @Override
    public int compareTo(User user) {
        int result = uuid.compareTo(user.uuid);
        if (result != 0) {
            return result;
        }
        result = userName.compareTo(user.userName);
        if (result != 0) {
            return result;
        }
        result = email.compareTo(user.email);
        if (result != 0) {
            return result;
        }
        return 0;
    }

    /**
     * Compares two users for equality. Returns {@code true} if and only if the argument is not null and is a
     * {@code User} object that has same {@code uuid}, {@code userName} and {@code email}.
     * 
     * @param obj
     *            the reference object with which to compare
     * @return {@code true} if this object is the same as the {@code obj} argument; {@code false} otherwise
     * @see User#hashCode
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;
        return (uuid == null ? user.uuid == null : uuid.equals(user.uuid)) &&
                (userName == null ? user.userName == null : userName.equals(user.userName)) &&
                (email == null ? user.email == null : email.equals(user.email));
    }

    /**
     * Returns a hash code value for this object. For result calculation used {@code uuid}, {@code userName} and
     * {@code email}.
     * 
     * @return a hash code value for this object
     * @see User#equals(Object)
     */
    @Override
    public int hashCode() {
        int result = 13;
        result = result * 37 + uuid.hashCode();
        result = result * 37 + userName.hashCode();
        result = result * 37 + email.hashCode();
        return result;
    }

    /**
     * Returns a string representation of this user. This is just the string representation of all fields and sizes of
     * collections.
     * 
     * @return a string representation of this user
     */
    @Override
    public String toString() {
        return getClass().getName() +
                "[id=" + id
                + ",uuid=" + uuid
                + ",userName=" + userName
                + ",firstName=" + firstName
                + ",lastName=" + lastName
                + ",email=" + email
                + ",password=" + password
                + ",salt=" + salt
                + ",rank=" + rank
                + ",rating=" + rating
                + ",activated=" + activated
                + ",banReason=" + banReason
                + ",banExpirationDate=" + banExpirationDate
                + ",birthday=" + birthday
                + ",registrationDate=" + registrationDate
                + ",lastLoginTime=" + lastLoginTime
                + ",role=" + role
                + ",articles.size()=" + articles.size()
                + ",comments.size()=" + comments.size()
                + "]";
    }

}
