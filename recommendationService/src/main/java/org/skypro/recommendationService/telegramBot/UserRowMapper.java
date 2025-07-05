package org.skypro.recommendationService.telegramBot;

import org.skypro.recommendationService.model.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRowMapper implements RowMapper<UserBot> {
    @Override
    public UserBot mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserBot userBot = new UserBot();
        userBot.setUserId((UUID) rs.getObject("id"));
        userBot.setNameUser(rs.getString("username"));
        return userBot;
    }
}