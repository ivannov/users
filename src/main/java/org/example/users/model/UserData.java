package org.example.users.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
public class UserData {

    @Id @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String prefix;

    public UserData() {
    }

    public UserData(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserData(String firstName, String lastName, String prefix) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.prefix = prefix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return Objects.equals(firstName, userData.firstName) &&
                Objects.equals(lastName, userData.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return prefix.toLowerCase() + " " + firstName.toLowerCase() + " " + lastName.toLowerCase();
    }
}
