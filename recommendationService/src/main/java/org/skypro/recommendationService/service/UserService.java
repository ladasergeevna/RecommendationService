package org.skypro.recommendationService.service;

import org.skypro.recommendationService.model.UserDeposit;
import org.skypro.recommendationService.model.UserWithdraw;
import org.skypro.recommendationService.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;
/**
 * Сервис для получения информации о поступлениях и снятиях.
 */

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    /**
     * Получает информацию о снятиях пользователя по его UUID.
     * Результат кэшируется.
     *
     * @param id UUID пользователя.
     * @return объект UserWithdraw с информацией о снятиях.
     */
    @Cacheable(value = "withdrawAmount", key = "#id")
    public UserWithdraw getWithdraws(UUID id) {
        return usersRepository.getUserWithdrawInfo(id);
    }

    /**
     * Получает информацию о поступлениях пользователя по его UUID.
     * Результат кэшируется.
     *
     * @param id UUID пользователя.
     * @return объект UserDeposit с информацией о поступлениях.
     */
    @Cacheable(value = "depositAmount", key = "#id")
    public UserDeposit getDeposits(UUID id) {
        return usersRepository.getUserDepositInfo(id);
    }
}


