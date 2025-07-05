package org.skypro.recommendationService.telegramBot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserBotRepository extends JpaRepository<UserBot, UUID> {
    List<UserBot> findByNameUser(String nameUser);
}
