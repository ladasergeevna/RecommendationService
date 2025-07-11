package org.skypro.recommendationService.telegramBot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

class UserRowMapperTest {
    @Mock
    private ResultSet mockResultSet;

    private UserRowMapper mapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mapper = new UserRowMapper();
    }

    @Test
    public void shouldMapResultSetToUserBotCorrectly() throws SQLException {
        UUID expectedId = UUID.randomUUID();
        String expectedUsername = "test_user";

        // Настройка поведения ResultSet
        given(mockResultSet.getObject("id")).willReturn(expectedId);
        given(mockResultSet.getString("username")).willReturn(expectedUsername);

        // Выполняем тестирование маппера
        UserBot mappedUser = mapper.mapRow(mockResultSet, anyInt());

        // Проверяем результаты
        assertThat(mappedUser.getUserId()).isEqualTo(expectedId);
        assertThat(mappedUser.getNameUser()).isEqualTo(expectedUsername);
    }

    @Test
    public void shouldHandleNullValuesGracefully() throws SQLException {
        // Эмулируем null-значения полей
        given(mockResultSet.getObject("id")).willReturn(null);
        given(mockResultSet.getString("username")).willReturn(null);

        // Тестируем маппер
        UserBot mappedUser = mapper.mapRow(mockResultSet, anyInt());

        // Проверяем значения (если поле необязательное и допустимо null)
        assertThat(mappedUser.getUserId()).isNull();
        assertThat(mappedUser.getNameUser()).isNull();
    }

    @Test
    public void shouldThrowSQLExceptionOnErrorReadingFromResultSet() throws SQLException {
        // Моделируем ошибку чтения из ResultSet
        given(mockResultSet.getObject("id")).willThrow(new SQLException("Ошибка SQL"));

        try {
            mapper.mapRow(mockResultSet, anyInt());
        } catch (SQLException e) {
            assertThat(e.getMessage()).contains("Ошибка SQL"); // Ожидаемая ошибка была выброшена
        }
    }
}