package org.skypro.recommendationService.service;

import org.skypro.recommendationService.model.UserDeposit;
import org.skypro.recommendationService.model.UserWithdraw;
import org.skypro.recommendationService.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Cacheable(value = "withdrawAmount", key = "#id")
    public UserWithdraw getWithdraws(UUID id) {
        return usersRepository.getUserWithdrawInfo(id);
    }

    @Cacheable(value = "depositAmount", key = "#id")
    public UserDeposit getDeposits(UUID id) {
        return usersRepository.getUserDepositInfo(id);
    }
}


