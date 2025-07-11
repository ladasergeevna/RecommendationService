package org.skypro.recommendationService.telegramBot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserBotServiceTest {
    @Mock
    private UserBotRepository userBotRepository;

    @InjectMocks
    private UserBotService userBotService;

    @Test
    void shouldReturnEmptyListWhenNoMatchingUsers() {
        given(userBotRepository.findByNameUser(anyString())).willReturn(Collections.emptyList());

        List<UserBot> result = userBotService.findUsersByUsername("nonexistent");

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnListOfMatchingUsers() {
        UserBot user1 = new UserBot();
        UserBot user2 = new UserBot();

        given(userBotRepository.findByNameUser("existing")).willReturn(List.of(user1, user2));

        List<UserBot> result = userBotService.findUsersByUsername("existing");

        assertThat(result).hasSize(2);
    }

}