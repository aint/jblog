package com.github.aint.jblog.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class represents a hub. The hub is just a container for articles.
 * 
 * @author Olexandr Tyshkovets
 * 
 * @see Entity
 * @see Article
 */
@javax.persistence.Entity
@Table(name = "HUB")
public class Hub implements Entity, Comparable<Hub> {
    private static final long serialVersionUID = 970204404158630774L;
    private Long id;
    private String name;
    private String description;
    private int rating;
    private boolean personal;
    private User author;
    private Set<Article> articles = new HashSet<Article>();

    /**
     * The default constructor for hibernate.
     */
    protected Hub() {
    }

    /**
     * Constructs a {@code Hub} with required fields.
     * 
     * @param name
     *            the hub's name
     * @param description
     *            the hub's description
     * @param personal
     *            the hub's type
     * @param author
     *            the hub's author
     * 
     * @see User
     */
    public Hub(String name, String description, boolean personal, User author) {
        this.name = name;
        this.description = description;
        this.personal = personal;
        this.author = author;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Id
    @GeneratedValue
    @Column(name = "HUB_ID")
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
     * @return the name
     */
    @Column(name = "NAME", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    @Column(name = "DESCRIPTION", columnDefinition = "TEXT", nullable = false)
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the rating
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
     * The hub can be public or personal. If it's public - anyone can add articles to it, if it's personal - only the
     * author.
     * 
     * @return {@code true} if the hub is a personal; {@code false} otherwise
     */
    @Column(name = "PERSONAL", nullable = false)
    public boolean isPersonal() {
        return personal;
    }

    /**
     * @param personal
     *            the personal to set
     */
    public void setPersonal(boolean personal) {
        this.personal = personal;
    }

    /**
     * @return the author
     * 
     * @see User
     */
    @ManyToOne
    @JoinColumn(name = "FK_USER", nullable = false)
    public User getAuthor() {
        return author;
    }

    /**
     * @param author
     *            the author to set
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * @return the articles
     * 
     * @see Article
     */
    @OneToMany(mappedBy = "hub")
    public Set<Article> getArticles() {
        return articles;
    }

    /**
     * @param articles
     *            the articles to set
     */
    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    /**
     * Compares two hubs for ordering. Compares by the {@code name} field.
     * 
     * @param hub
     *            the hub to be compared
     * @return a negative integer, zero, or a positive integer as this hub is less than, equal to, or greater than the
     *         specified {@code hub}
     * @throws NullPointerException
     *             if the specified {@code hub} is {@code null}
     */
    @Override
    public int compareTo(Hub hub) {
        int result = name.compareTo(hub.name);
        if (result != 0) {
            return result;
        }
        return 0;
    }

    /**
     * Compares two hubs for equality. Returns {@code true} if and only if the argument is not {@code null} and is a
     * {@code Hub} object that has the same {@code name} field.
     * 
     * @param obj
     *            the reference object with which to compare
     * @return {@code true} if this object is the same as the {@code obj} argument; {@code false} otherwise
     * @see Hub#hashCode
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Hub)) {
            return false;
        }
        Hub hub = (Hub) obj;
        return (name == null ? hub.name == null : name.equals(hub.name));
    }

    /**
     * Returns a hash code value for this object. For result calculation used the {@code name} field.
     * 
     * @return a hash code value for this object
     * @see Hub#equals(Object)
     */
    @Override
    public int hashCode() {
        int result = 13;
        result = result * 37 + name.hashCode();
        return result;
    }

    /**
     * Returns a string representation of the hub. This is just the string representation of all fields and sizes of
     * collections.
     * 
     * @return a string representation of the hub
     */
    @Override
    public String toString() {
        return getClass().getName() +
                "[id=" + id
                + ",name=" + name
                + ",description=" + description
                + ",rating=" + rating
                + ",personal=" + personal
                + ",author=" + author.getUserName()
                + ",articles.size()=" + articles.size()
                + "]";
    }

}
