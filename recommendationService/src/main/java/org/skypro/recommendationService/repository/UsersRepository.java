package org.skypro.recommendationService.repository;

import org.skypro.recommendationService.model.UserDeposit;
import org.skypro.recommendationService.model.UserWithdraw;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UsersRepository {
    private final JdbcTemplate jdbcTemplate;

    public UsersRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Метод для получения информации о пользователе из таблицы users_withdraw
    public UserWithdraw getUserWithdrawInfo(String id) {

        return jdbcTemplate.queryForObject(
                "SELECT user_id, debit_amount, saving_amount, credit_amount, invest_amount FROM users_withdraws WHERE user_id = ?",
                (rs, rowNum) -> new UserWithdraw(
                        rs.getObject("user_id", UUID.class),
                        rs.getInt("debit_amount"),
                        rs.getInt("saving_amount"),
                        rs.getInt("credit_amount"),
                        rs.getInt("invest_amount")),
                id);
    }

    // Метод для получения информации о пользователе из таблицы users_deposit

    public UserDeposit getUserDepositInfo(String id) {
        return jdbcTemplate.queryForObject(
                "SELECT user_id, debit_amount, saving_amount, credit_amount, invest_amount FROM users_deposits WHERE user_id = ?",
                (rs, rowNum) -> new UserDeposit(
                        rs.getObject("user_id", UUID.class),
                        rs.getInt("debit_amount"),
                        rs.getInt("saving_amount"),
                        rs.getInt("credit_amount"),
                        rs.getInt("invest_amount")),
                id);
    }
}
