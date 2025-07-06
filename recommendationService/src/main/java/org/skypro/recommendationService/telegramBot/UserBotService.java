package org.skypro.recommendationService.telegramBot;

import org.skypro.recommendationService.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Сервис для работы с пользователями бота.
 */
@Service
public class UserBotService {
    @Autowired
    private UserBotRepository userBotRepository;
    /**
     * Находит пользователей по имени пользователя.
     *
     * @param nameUser имя пользователя для поиска.
     * @return список пользователей, соответствующих имени.
     */
    public List<UserBot> findUsersByUsername(String nameUser) {
        return userBotRepository.findByNameUser(nameUser);
    }
}
