package org.skypro.recommendationService.repository;

import org.skypro.recommendationService.model.UserDeposit;
import org.skypro.recommendationService.model.UserWithdraw;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;
/**
 * Репозиторий для работы с данными пользователей в базе данных.
 */
@Repository
public class UsersRepository {
    private final JdbcTemplate jdbcTemplate;

    public UsersRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Получает информацию о пользователе из таблицы users_withdraws по его ID.
     *
     * @param id UUID пользователя.
     * @return объект UserWithdraw с данными о выводах пользователя.
     */
    public UserWithdraw getUserWithdrawInfo(UUID id) {

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

    /**
     * Получает информацию о пользователе из таблицы users_deposits по его ID.
     *
     * @param id UUID пользователя.
     * @return объект UserDeposit с данными о депозитах пользователя.
     */

    public UserDeposit getUserDepositInfo(UUID id) {
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
