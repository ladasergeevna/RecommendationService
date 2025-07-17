package org.skypro.recommendationService.model;

import java.util.Objects;
import java.util.UUID;

public class User {
    private UUID userId;
    private String nameUser;

    public User(UUID userId, String nameUser) {
        this.userId = userId;
        this.nameUser = nameUser;
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
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

}
