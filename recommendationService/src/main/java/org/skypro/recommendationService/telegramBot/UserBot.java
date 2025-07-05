package org.skypro.recommendationService.telegramBot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.skypro.recommendationService.model.User;
import jakarta.persistence.Id;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "USERS")
public class UserBot {
    @Id
    @Column(name = "ID")
    private UUID userId;
    @Column(name = "USERNAME")
    private String nameUser;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;

    public UserBot(UUID userId, String nameUser, String firstName, String lastName) {
        this.userId = userId;
        this.nameUser = nameUser;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserBot() {

    }


    public UUID getUserId() {
        return userId;
    }

    public String getNameUser() {
        return nameUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBot userBot = (UserBot) o;
        return Objects.equals(userId, userBot.userId) && Objects.equals(nameUser, userBot.nameUser) && Objects.equals(firstName, userBot.firstName) && Objects.equals(lastName, userBot.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
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
}
