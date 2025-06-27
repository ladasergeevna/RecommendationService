package org.skypro.recommendationService.service;

import org.skypro.recommendationService.model.UserDeposit;
import org.skypro.recommendationService.model.UserWithdraw;
import org.skypro.recommendationService.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    //User with withdraw transaction
    public UserWithdraw getWithdraws(String id) {
        return usersRepository.getUserWithdrawInfo(id);
    }

    //User with deposit transaction
    public UserDeposit getDeposits(String id) {
        return usersRepository.getUserDepositInfo(id);
    }
}
