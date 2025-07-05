package org.skypro.recommendationService.telegramBot;

import org.skypro.recommendationService.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBotService {
    @Autowired
    private UserBotRepository userBotRepository;
    public List<UserBot> findUsersByUsername(String nameUser) {
        return userBotRepository.findByNameUser(nameUser);
    }
}
