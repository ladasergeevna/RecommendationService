package org.skypro.recommendationService.telegramBot;

import org.skypro.recommendationService.model.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
/**
 * Маппер строк ResultSet в объект UserBot.
 */
public class UserRowMapper implements RowMapper<UserBot> {
    /**
     * Преобразует текущую строку ResultSet в объект UserBot.
     *
     * @param rs текущая строка результата запроса.
     * @param rowNum номер строки результата.
     * @return объект UserBot, соответствующий текущей строке.
     * @throws SQLException если произошла ошибка при чтении данных из ResultSet.
     */
    @Override
    public UserBot mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserBot userBot = new UserBot();
        userBot.setUserId((UUID) rs.getObject("id"));
        userBot.setNameUser(rs.getString("username"));
        return userBot;
    }
}