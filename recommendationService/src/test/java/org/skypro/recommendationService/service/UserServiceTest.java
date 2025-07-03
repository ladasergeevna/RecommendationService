package org.skypro.recommendationService.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skypro.recommendationService.model.UserDeposit;
import org.skypro.recommendationService.model.UserWithdraw;
import org.skypro.recommendationService.repository.UsersRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private CacheManager cacheManager;

    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWithdraws() throws Exception {

        UserWithdraw withdraw = new UserWithdraw(userId,100,200,300,400);
        when(usersRepository.getUserWithdrawInfo(userId)).thenReturn(withdraw);

        assertThat(userService.getWithdraws(userId)).isEqualTo(withdraw);
        verify(usersRepository).getUserWithdrawInfo(userId);

    }

    @Test
    void testGetDeposits() throws Exception {

        UserDeposit deposit = new UserDeposit(userId, 100, 200, 300, 400); // Инициализируйте необходимые поля объекта
        when(usersRepository.getUserDepositInfo(userId)).thenReturn(deposit);

        assertThat(userService.getDeposits(userId)).isEqualTo(deposit);
        verify(usersRepository).getUserDepositInfo(userId);
    }

}